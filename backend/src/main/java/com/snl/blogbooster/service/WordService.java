package com.snl.blogbooster.service;

import com.snl.blogbooster.common.CommonUtil;
import com.snl.blogbooster.mapper.WordMapper;
import com.snl.blogbooster.model.domain.RankerPosting;
import com.snl.blogbooster.model.domain.SearchResult;
import com.snl.blogbooster.model.domain.UserRankHistory;
import com.snl.blogbooster.model.domain.Word;
import com.snl.blogbooster.model.dto.InfluenceRequestDto;
import com.snl.blogbooster.model.dto.KeywordResponseDto;
import com.snl.blogbooster.model.dto.UserRequestDto;
import com.snl.blogbooster.model.dto.WordRequestDto;
import kr.co.shineware.nlp.komoran.constant.DEFAULT_MODEL;
import kr.co.shineware.nlp.komoran.core.Komoran;
import kr.co.shineware.nlp.komoran.model.KomoranResult;
import kr.co.shineware.nlp.komoran.model.Token;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.json.JSONArray;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.security.Security;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Slf4j
@Service
@RequiredArgsConstructor
public class WordService {

    private final WordMapper wordMapper;
    private final PostingService postingService;
    private TreeMap<String, Integer> wordMap = new TreeMap<>();
    private TreeMap<String, Integer> keywordMap = new TreeMap<>();
    private TreeMap<String, Integer> rankerKeywordMap = new TreeMap<>();
    static List<String> keywords = new ArrayList<>();
    private final static String[] WORD_TYPE ={"NORMAL","KEYWORD"};

    public void calculateWordCount(WordRequestDto wordRequestDto)
    {
        String url = wordRequestDto.getUrl();
        String keyword = wordRequestDto.getKeyword();
        Elements blogMainContents = postingService.getBlogMainContents(url);
        Elements spans = blogMainContents.select("span");
        String komoranedText = "";
        for(Element e : spans)
        {
            String text = e.text();
            text.replaceAll("[^a-zA-Zㄱ-힣]", "").length();
            if(!"".equals(text))
            {
                String fixText = komoranAnalyzer(text);
                if("".equals(komoranedText))
                {
                    komoranedText=fixText;
                }
                else
                {
                    komoranedText +=System.lineSeparator();
                    komoranedText +=fixText;
                }
            }
        }
        if(keyword.isEmpty() || keyword == null)//일반 단어 반복수 세는함수 호출
        {
            calculateNormalWordCount(komoranedText, url);
        }
        else//입력받은 키워드 반복수 세는 함수 호출
        {
            calculateKeywordCount(komoranedText, url, keyword);
        }
    }
    public void calculateKeywordCount(String komoranedText, String url, String keyword)
    {
        getPermutationList(keyword);
        boolean isMyBlog = true;
        //제목에서 추출한 키워드로 순열 만들기

        String[]  finalTextArray = komoranedText.split("\\n");
        for(String text : finalTextArray)
        {
            keywordCount(text,isMyBlog);
        }
        List<Map.Entry<String,Integer>> keyWordList;
        if(isMyBlog)
        {
            keyWordList= new ArrayList<>(keywordMap.entrySet());
        }
        else
        {
            keyWordList= new ArrayList<>(rankerKeywordMap.entrySet());
        }
        for(Map.Entry<String, Integer> entry : keyWordList)
        {
            if(entry.getValue() > 0)
            {
                Word word = Word.builder()
                        .repeatCount(entry.getValue())
                        .registerUserId("ckrzkssja123")
                        .wordType(WORD_TYPE[1])
                        .value(entry.getKey())
                        .url(url).build();

                wordMapper.insertWord(word);
            }
        }
    }

    public void getPermutationList(String fixedTitle)
    {
        String []tempArr = fixedTitle.split("\\s");
        // 공백으로 단어구분
        if(tempArr.length < 2)
        {
            String keyword = tempArr.length == 0 ? fixedTitle : tempArr[0];
            keywords.add(keyword);
        }
        List<String> arr = new ArrayList<>();
        int index =0;
        for(String word : tempArr)
        {
            if(!"".equals(word)||word.contains(" "))
            {
                arr.add(word);
            }
        }
        int n = arr.size();
        boolean[] visited = new boolean[n];
        String [] output = new String[n];
        for(int i=2; i<n+1; i++)
        {
            perm(arr, output, visited, 0, n, i);
        }
    }

    // 사전순으로 순열 구하기
    public void perm(List<String> arr, String[] output, boolean[] visited, int depth, int n, int r) {
        if (depth == r) {
            setKeyword(output, r);
            return;
        }

        for (int i = 0; i < n; i++) {
            if (visited[i] != true) {
                visited[i] = true;
                output[depth] = arr.get(i);
                perm(arr, output, visited, depth + 1, n, r);
                visited[i] = false;
            }
        }
    }

    //제목으로 유추할수 있는 키워드 조합
    public void setKeyword(String[] arr, int r) {
        String keyword = arr[0]; //단어사이에 공백있음
        String noSpaceKeyword = arr[0];//단어사이에 공백없음
        for (int i = 1; i < r; i++) {
            keyword = keyword+" "+arr[i];
            noSpaceKeyword =noSpaceKeyword+arr[i];
        }
        keywords.add(keyword);
        keywords.add(noSpaceKeyword);
    }

    public void keywordCount(String sentence, boolean isMyBlog){

        for(String keyword : keywords)
        {
            int count =0;
            int pos = sentence.indexOf(keyword); // 한줄에 키워드가 여러번들어갔을 경우를 고려함
            while(pos != -1)
            {
                count++;
                pos = sentence.indexOf(keyword,pos+1);
            }
            if(isMyBlog)
            {
                Integer num = keywordMap.get(keyword);
                if(num==null) num =0;
                keywordMap.put(keyword,num+count);
            }
            else{
                Integer num = rankerKeywordMap.get(keyword);
                if(num==null) num =0;
                rankerKeywordMap.put(keyword,num+count);
            }
        }
    }

    /* 형태소를 제거한 본문 단어반복수 세기*/
    public void calculateNormalWordCount(String komoranedText, String url)
    {
        String[] sentenceArray = komoranedText.split("\\s");
        for(String word : sentenceArray){
            if(!word.isEmpty()&& word != null && word !=" ")
            {
                Integer num = wordMap.get(word);
                if(num==null) num = 0;
                wordMap.put(word, num+1);
            }
        }

        //반복된 단어를 넣기전에 기존에 calc했던 정보를 삭제
        wordMapper.deleteWordHistory(url);
        for(Map.Entry<String, Integer> entry : wordMap.entrySet())
        {
            int repeatCount = entry.getValue();
            if(repeatCount > 2) // 두번반복 이상인것만 Table에 넣는다.
            {
                Word word = Word.builder()
                        .value(entry.getKey())
                        .repeatCount(repeatCount)
                        .url(url)
                        .registerUserId("ckrzkssja123") // 다음에 pearl에서 한것처럼 빼야함
                        .wordType(WORD_TYPE[0])
                        .build();
                wordMapper.insertWord(word);
            }
        }
    }

    /* 형태소 제거 */
    public String komoranAnalyzer(String text)
    {
        Komoran komoran = new Komoran(DEFAULT_MODEL.FULL);

        KomoranResult analyzeResultList = komoran.analyze(text);

        List<Token> tokenList = analyzeResultList.getTokenList();
        Map<Integer,Integer> deletedTarget = new HashMap<>();
        for (Token token : tokenList) {
            if(token.getPos().charAt(0) == 'J')
            {
                deletedTarget.put(token.getBeginIndex(),token.getEndIndex());
            }
        }
        int deletedLength = 0;
        List<Map.Entry<Integer,Integer>> deletedWords = new ArrayList<>(deletedTarget.entrySet());
        deletedWords.sort(Map.Entry.comparingByValue());
        for(Map.Entry<Integer,Integer> entry : deletedWords)
        {
            int startPos, endPos;
            startPos = entry.getKey();
            endPos = entry.getValue();
            int startPoint = startPos-deletedLength;
            int endPoint  = endPos-deletedLength;
            text = text.substring(0,startPoint)+text.substring(endPoint);
            deletedLength += endPos-startPos;
        }
        return text;
    }


}
