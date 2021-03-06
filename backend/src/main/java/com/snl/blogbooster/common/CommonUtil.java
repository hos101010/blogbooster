package com.snl.blogbooster.common;

import com.snl.blogbooster.model.domain.RankerPosting;
import com.snl.blogbooster.model.domain.user.User;
import com.snl.blogbooster.model.domain.userScoreHistory.UserScoreHistory;
import com.snl.blogbooster.model.dto.keyword.KeywordResponseDto;
import lombok.extern.slf4j.Slf4j;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.json.JSONArray;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

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
public class CommonUtil {

    private final static String VIEW_SEARCH_URL ="https://m.search.naver.com/search.naver?where=view&sm=tab_jum&query=";
    private final static int IN_A_DAY = 0;
    private final static int IN_A_WEEK = -6;
    private final static int IN_A_MONTH = -29;
    private final static String BLOG_SEARCH_URL ="https://search.naver.com/search.naver?where=blog&query=";
    private final static String DAILY_FILTER_PARAM ="&sm=tab_opt&nso=so:r,p:from";
    private final static String START_POINT_PARAM ="&start=3900";
    private final static String AD_SEARCH_URL ="https://api.naver.com/keywordstool?hintKeywords=";
    private final static String AD_API_KEY ="010000000077bc0fbaa9853289ee2c5f94912882697a19af92052382c0252fe9894c16ed91";
    private final static String AD_SECRET="AQAAAAB3vA+6qYUyie4sX5SRKIJpu19MDLVdUMdizjPQboYYYw==";
    private final static String AD_CUSTOMER_ID="2136251";
    private final static String PROVIDER = "BC";
    private final static String HMAC_SHA256 = "HMac-SHA256";
    private final static String CLIENT_ID ="aZZq1Iyq0RGLaoZEWnJc";
    private final static String CLIENT_SECRET ="aE2SoroDlq";
    private final static String HTML_EXCEPTIONS ="<(/)?([a-zA-Z]*)(\\s[a-zA-Z]*=[^>]*)?(\\s)*(/)?>";
    private final static String BLOG_API_SEARCH_URL ="https://openapi.naver.com/v1/search/blog?query=";
    private final static String LIST_PARM = "?categoryNo=0&listStyle=post";
    private final static String BLOG_DEFUALT_URL = "https://m.blog.naver.com/";


    /* URL????????? document Page????????????*/
    public static Document getDocumentByUrl(String url)
    {
        Document document=null;
        try{
            document = Jsoup.connect(url).get();
        }catch (Exception e)
        {
            log.error("[ERROR]getDocumentByUrl : {}",e.getMessage());
        }
        return document;
    }

    /* */
    public static String getyyyyMmddDate()
    {
        LocalDate now = LocalDate.now();// default Timezone is korea
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
        String formattedNow = now.format(formatter);
        return formattedNow;
    }

    /* ?????? ???????????? */
    public static String AddDate(String strDate, int year, int month, int day)
    {
        String result=strDate;
        try{
            SimpleDateFormat dtFormat = new SimpleDateFormat("yyyyMMdd");
            Calendar cal = Calendar.getInstance();
            Date dt = dtFormat.parse(strDate);
            cal.setTime(dt);
            cal.add(Calendar.YEAR, year);
            cal.add(Calendar.MONTH, month);
            cal.add(Calendar.DATE, day);
            strDate=  dtFormat.format(cal.getTime());
        }catch(Exception e)
        {
            log.error("[ERROR]CommonUtil.AddDate : {}"+e.getMessage());
        }
        return strDate;
    }

    /* response ?????? */
    public static String readBody(InputStream body){
        InputStreamReader streamReader = new InputStreamReader(body);
        try (BufferedReader lineReader = new BufferedReader(streamReader)) {
            StringBuilder responseBody = new StringBuilder();
            String line;
            while ((line = lineReader.readLine()) != null) {
                responseBody.append(line);
            }
            return responseBody.toString();
        } catch (IOException e) {
            throw new RuntimeException("API ????????? ????????? ??????????????????.", e);
        }
    }

    /* HTTP ??????*/
    public static HttpURLConnection connect(String apiUrl){
        try {
            URL url = new URL(apiUrl);
            return (HttpURLConnection)url.openConnection();
        } catch (MalformedURLException e) {
            throw new RuntimeException("API URL??? ?????????????????????. : " + apiUrl, e);
        } catch (IOException e) {
            throw new RuntimeException("????????? ??????????????????. : " + apiUrl, e);
        }
    }

    /* GET REQUEST */
    public static String get(String apiUrl, Map<String, String> requestHeaders){
        HttpURLConnection con = connect(apiUrl);
        try {
            con.setRequestMethod("GET");
            for(Map.Entry<String, String> header :requestHeaders.entrySet()) {
                con.setRequestProperty(header.getKey(), header.getValue());
            }
            int responseCode = con.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) { // ?????? ??????
                return readBody(con.getInputStream());
            } else { // ?????? ??????
                return readBody(con.getErrorStream());
            }
        } catch (IOException e) {
            throw new RuntimeException("API ????????? ?????? ??????", e);
        } finally {
            con.disconnect();
        }
    }


    /* ????????? ?????? */
    public static boolean isInteger(String value)
    {
        if(value == null || "".equals(value)) {
            return false;
        }

        try {
            int iVal = Integer.parseInt(value);
            return true;
        }
        catch(NumberFormatException e) {
        }
        return false;
    }

    /* Int??? ?????? ??????????????? default ??? ?????? */
    public static int isIntegerValue(JSONObject jsonObject, String key, int defaultValue)
    {
        if(jsonObject == null || "".equals(jsonObject)) {
            return defaultValue;
        }

        try {
           return jsonObject.getInt(key);
        }
        catch(Exception e) {
            return defaultValue;
        }
    }

    /* Selenium getText */
    public static String getParsingTextByTag(ChromeDriver driver, String selectorUrl, String defaultValue)
    {
        try{
            String value = driver.findElement(new By.ByCssSelector(selectorUrl)).getText();
            return value;
        }
        catch (Exception e)
        {
            return defaultValue;
        }
    }
    /* ????????? View??? 5????????? ??????*/
    public static List<String> getKeywordViewRankers(String keyword)
    {
        List<String> rankers = new ArrayList<>();
        String []fixedKeyword =keyword.split("\\s");
        String viewKeyword = "";
        for(int i=0; i<fixedKeyword.length; i++)//view tab????????? ????????? ????????? ????????? "+"??? ?????????
        {
            viewKeyword +=fixedKeyword[i];
            if(i<fixedKeyword.length-1)
            {
                viewKeyword+="+";
            }
        }

        if("".equals(viewKeyword))
        {
            viewKeyword=keyword;
        }

        String blogUrl =null;
        int ranking =0;
        try{
            Document doc = Jsoup.connect(VIEW_SEARCH_URL+viewKeyword).get();
            Elements viewTab = doc.select("div._svp_list ul.lst_total");
            Elements viewRanker = viewTab.select("li");
            for(Element element : viewRanker)
            {
                System.out.println(viewRanker.toString());
                ranking = Integer.parseInt(element.select("li.bx._svp_item").attr("data-cr-rank"));
                blogUrl = element.select("a.api_txt_lines.total_tit._cross_trigger").attr("href");
                if(ranking < 6 && blogUrl.contains("blog.naver.com"))
                {
                    rankers.add(blogUrl);
                }
                if(ranking == 5)
                    break;
            }

        }
        catch(Exception e)
        {
            log.info("ViewSearchError keyword : {}, url:{} , ranking:{},  error: {}",keyword,blogUrl, ranking,e.getMessage() );
        }

        return rankers;
    }


    /* ????????? View??? 5????????? ??????*/
    public static List<RankerPosting> getKeywordViewRanker(String keyword)
    {
        List<RankerPosting> rankers = new ArrayList<>();
        String []fixedKeyword =keyword.split("\\s");
        String viewKeyword = "";
         for(int i=0; i<fixedKeyword.length; i++)//view tab????????? ????????? ????????? ????????? "+"??? ?????????
        {
            viewKeyword +=fixedKeyword[i];
            if(i<fixedKeyword.length-1)
            {
                viewKeyword+="+";
            }
        }

        if("".equals(viewKeyword))
        {
            viewKeyword=keyword;
        }

        try{
            Document doc = Jsoup.connect(VIEW_SEARCH_URL+viewKeyword).get();
            Elements viewTab = doc.select("ul[class=\"lst_total _list_base\"]");
            Elements viewRanker = viewTab.select("li");
            for(Element element : viewRanker)
            {
                int ranking = Integer.parseInt(element.select("li.bx._svp_item").attr("data-cr-rank"));
                String blogUrl = element.select("a.api_txt_lines.total_tit._cross_trigger").attr("href");
                if(ranking < 6 && blogUrl.contains("blog.naver.com"))
                {
                    String influence = element.select("span.sub_txt.stress").text();
                    boolean isInfluence = (influence == "???????????????"||influence.equals("???????????????")) ? true : false;
                    String registerTime = element.select("span.sub_time.sub_txt").text();
                    String resisterUserName = element.select("a.sub_txt.sub_name").text();
                    String title = element.select( "a.api_txt_lines.total_tit._cross_trigger").text();
                    String registerUserId = blogUrl.split("blog.naver.com")[1].split("/")[1];
                    long postingNum = Long.parseLong(blogUrl.split("blog.naver.com")[1].split("/")[2]);
                    Elements tags = element.select("div.total_tag_area a span.txt");
                    List<String> blogTags = new ArrayList<>();
                    for(Element tag : tags)
                    {
                        String blogTag = tag.text().replace("#","");
                        blogTags.add(blogTag);
                    }
                    RankerPosting rankerPosting = RankerPosting.builder()
                            .registerUserName(resisterUserName)
                            .registerUserId(registerUserId)
                            .postdate(registerTime)
                            .ranking(ranking)
                            .title(title)
                            .isInfluence(isInfluence)
                            .postingNum(postingNum)
                            .url(blogUrl)
                            .tags(blogTags).build();
                    rankers.add(rankerPosting);
                }
                if(ranking == 5)
                    break;
            }

        }
        catch(Exception e)
        {
            log.info("ViewSearchError : {}",e.getMessage() );
        }

        return rankers;
    }

    public static KeywordResponseDto getKeyWordInfo(String keyword)
    {
        /* ??????,??????,?????? ????????? */
        int dayPublishCount = getPublicationCount(keyword,IN_A_DAY);
        int weekPublishCount = getPublicationCount(keyword,IN_A_WEEK);
        int monthPublishCount = getPublicationCount(keyword,IN_A_MONTH);

        /* ?????? ?????????*/
        int totalPublishCount = getBlogTotalPublishCount(keyword);

        /* ?????? ?????? ???*/
        KeywordResponseDto keywordResponseDto = getMontlySearchMount(keyword);
        keywordResponseDto.setDayPublishCount(dayPublishCount);
        keywordResponseDto.setMonthPublishCount(monthPublishCount);
        keywordResponseDto.setTotalPublishCount(totalPublishCount);
        keywordResponseDto.setWeekPublishCount(weekPublishCount);

        return keywordResponseDto;
    }

    /* ????????? ??? ????????? ??????*/
    public static KeywordResponseDto getMontlySearchMount(String keyword){
        JSONObject result = null;
        try{
            keyword = keyword.replaceAll("\\s","");//????????? ????????????
            keyword = URLEncoder.encode(keyword, StandardCharsets.UTF_8);
            Security.addProvider(new BouncyCastleProvider());
            Mac mac = Mac.getInstance(HMAC_SHA256, PROVIDER);
            mac.init(new SecretKeySpec(AD_SECRET.getBytes(), HMAC_SHA256));
            Map<String, String> requestHeaders = new HashMap<>();
            String timestamp = String.valueOf(System.currentTimeMillis());
            String data =timestamp+"."+"GET"+"."+"/keywordstool";
            String signature = DatatypeConverter.printBase64Binary(mac.doFinal(data.getBytes()));
            requestHeaders.put("X-Timestamp", timestamp);
            requestHeaders.put("X-API-KEY", AD_API_KEY);
            requestHeaders.put("X-API-SECRET", AD_SECRET);
            requestHeaders.put("X-CUSTOMER", AD_CUSTOMER_ID);
            requestHeaders.put("X-Signature", signature);
            String responseBody = CommonUtil.get(AD_SEARCH_URL+keyword+"&showDetail=1",requestHeaders);
            JSONObject jsonObject = new JSONObject(responseBody);
            JSONArray jsonArray = jsonObject.getJSONArray("keywordList");
            result =  jsonArray.getJSONObject(0);
        }
        catch (Exception e)
        {
            log.info("ERROR-calculateMontlySearchMount : {}",e.getMessage());
        }

        int pcSearchCount = CommonUtil.isIntegerValue(result,"monthlyPcQcCnt",10);
        int mobileSearchCount  =CommonUtil.isIntegerValue(result,"monthlyMobileQcCnt",10);
        int totalSearchCount =pcSearchCount+mobileSearchCount;


        KeywordResponseDto keywordSearchResponseDto = KeywordResponseDto.builder()
                .pcSearchCount(pcSearchCount)
                .mobileSearchCount(mobileSearchCount)
                .totalSearchCount(totalSearchCount)
                .complexIndex(result.getString("compIdx") == null ? "??????" : result.getString("compIdx"))
                .build();

        return keywordSearchResponseDto;
    }


    /* ????????? View??? ????????? ?????? (In a Day , In a week , In a month )*/
    public static int getPublicationCount(String keyword, int publishType)
    {
        keyword = keyword.replaceAll("\\s","");
        int publicationCount =0;
        LocalDate now = LocalDate.now();// default Timezone is korea
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
        String formattedNow = now.format(formatter);
        try{
            String endDate = CommonUtil.AddDate(formattedNow,0,0,publishType);
            while(true)
            {
                //??? ????????? ??????
                String period = endDate+"to"+endDate;
                publicationCount+=calculateDailyPublicationCount(keyword,period);
                if(endDate.equals(formattedNow))//???????????? ???????????? break;
                    break;
                endDate = CommonUtil.AddDate(endDate,0,0,1);
            }
        }catch(Exception e)
        {
            return publicationCount;
        }
        return publicationCount;
    }

    /* ????????? ????????? ??????*/
    public static int calculateDailyPublicationCount(String viewKeyword, String period )
    {
        int result =0;
        try{
            Document doc = Jsoup.connect(BLOG_SEARCH_URL+viewKeyword+ DAILY_FILTER_PARAM+period+START_POINT_PARAM).get();
            Elements viewTab = doc.select("ul.lst_total");
            Elements viewRanker = viewTab.select("li");
            String lastNumberString = viewRanker.last().attr("id").split("blog_")[1];
            result =Integer.parseInt(lastNumberString);
        }
        catch(Exception e)
        {
            return 0;
        }
        return result;
    }

    /* ????????? ?????? blog ??? ????????? */
    public static int getBlogTotalPublishCount(String keyword) {

        int result =0;
        String text = URLEncoder.encode(keyword, StandardCharsets.UTF_8);

        String apiURL = BLOG_API_SEARCH_URL + text;    // json ??????

        Map<String, String> requestHeaders = new HashMap<>();
        requestHeaders.put("X-Naver-Client-Id", CLIENT_ID);
        requestHeaders.put("X-Naver-Client-Secret", CLIENT_SECRET);
        String responseBody = CommonUtil.get(apiURL, requestHeaders);

        JSONObject jsonObject = new JSONObject(responseBody);
        result = jsonObject.getInt("total");
        return result;
    }

    /* ????????? blog??? ranker ??????*/
    public static List<RankerPosting> getKeywordBlogRanker(String keyword)
    {
        List<RankerPosting> rankers = new ArrayList<>();

        String text = URLEncoder.encode(keyword, StandardCharsets.UTF_8);

        String apiURL = BLOG_API_SEARCH_URL + text;    // json ??????

        Map<String, String> requestHeaders = new HashMap<>();
        requestHeaders.put("X-Naver-Client-Id", CLIENT_ID);
        requestHeaders.put("X-Naver-Client-Secret", CLIENT_SECRET);
        String responseBody = CommonUtil.get(apiURL,requestHeaders);

        JSONObject jsonObject = new JSONObject(responseBody);
        Integer totalBlogCount = (Integer) jsonObject.get("total");
        JSONArray jsonArray = jsonObject.getJSONArray("items");
        int ranking =1;
        for(Object jObject : jsonArray)
        {
            JSONObject temp  = new JSONObject(jObject.toString());
            String title= (String)temp.get("title");
            String link = (String)temp.get("link");
            String description = (String)temp.get("description");
            String bloggerName = (String)temp.get("bloggername");
            String postdate = (String)temp.get("postdate");

            String[] blog_logNo = link.split("\\?");
            String[] logNo_split = link.split("=");
            String registerUserId = null;
            if(link.contains("blog.naver.com"))
            {
                link = blog_logNo[0]+"/"+logNo_split[2];
                registerUserId = blog_logNo[0].split(".com/")[1];
            }

            RankerPosting rankerPosting = RankerPosting.builder()
                    .description(description.replaceAll(HTML_EXCEPTIONS,""))
                    .registerUserId(registerUserId)
                    .postdate(postdate.replaceAll(HTML_EXCEPTIONS,""))
                    .registerUserName(bloggerName.replaceAll(HTML_EXCEPTIONS,""))
                    .url(link.replaceAll(HTML_EXCEPTIONS,""))
                    .ranking(ranking)
                    .title(title.replaceAll(HTML_EXCEPTIONS,"")).build();
            rankers.add(rankerPosting);
            ranking++;
        }

        return rankers;
    }


    /*???????????? ?????? ?????? ????????????
      param : userId (ex. ckrzkssja123)
    * result :  UserScoreHistory
    * */
    public static UserScoreHistory getUserScore(String userId)
    {
        ChromeDriver driver;
        System.setProperty("webdriver.chrome.driver", "C://Users//?????????//Desktop//chromedriver_win32//chromedriver.exe");
        // 2. WebDriver ?????? ??????
        ChromeOptions opt = new ChromeOptions();
        opt.addArguments("headless");         //??????????????? ????????? ???????????????.
        opt.addArguments("--blink-settings=imagesEnabled=false"); //????????? ?????? ???????????????.
        driver = new ChromeDriver(opt);

        WebDriverWait wait = new WebDriverWait(driver,10);
        JavascriptExecutor js = (JavascriptExecutor) driver;

        // Launch the application
        driver.get(BLOG_DEFUALT_URL+userId);

        String visitorCountText = driver.findElement(new By.ByCssSelector("#root > div.blog_cover__Il6gZ > div > div.title_area__W05iJ > div")).getText();
        String blogThema = CommonUtil.getParsingTextByTag(driver,"#root > div.blog_cover__Il6gZ > div > div.bloger_area___GicX > div.text_area___K6nq > div.bloger_info__KrWs1 > span.subject__CBtO5","??????");
        String neborCount = driver.findElement(new  By.ByCssSelector("#root > div.blog_cover__Il6gZ > div > div.bloger_area___GicX > div.text_area___K6nq > div.bloger_info__KrWs1 > span.buddy___ckI_")).getText();
        blogThema = blogThema.replaceAll("???","");

        //move to category page
        wait.until(ExpectedConditions.presenceOfElementLocated(new By.ByCssSelector("#root > div.blog_cover__Il6gZ > div > div.btn_area__OtwBw > div:nth-child(2) > button")));
        WebElement el = driver.findElement(new By.ByCssSelector("#root > div.blog_cover__Il6gZ > div > div.btn_area__OtwBw > div:nth-child(2) > button"));
        el.click();//???????????? ?????? ????????? ??????

        //????????? ??????????????? ??? ????????? ????????????
        wait.until(ExpectedConditions.presenceOfElementLocated(new By.ByCssSelector("#root > div.apollo_layer_container > div > div > div > ul > li > a > div > em")));
        WebElement totalPublication = driver.findElement(new By.ByCssSelector("#root > div.apollo_layer_container > div > div > div > ul > li > a > div > em"));
        String totalCountText = totalPublication.getText();
        long totalCount = Integer.parseInt(totalCountText.replaceAll(",",""));
        String [] array = visitorCountText.split("\\s");
        long dayVisitor = Integer.parseInt(array[1].replaceAll(",",""));
        long totalVisitor = Integer.parseInt(array[3].replaceAll(",",""));
        long neighborCount =Integer.parseInt(neborCount.replaceAll("?????? ??????","").replaceAll(",",""));

        UserScoreHistory userRankHistory = UserScoreHistory.builder()
                .totalPostingCount(totalCount)
                .dayVisitor(dayVisitor)
                .neighborCount(neighborCount)
                .userId(userId)
                .standardDate(CommonUtil.getyyyyMmddDate())
                .totalVisitor(totalVisitor).build();

        //??????????????? 0????????? Score???????????? ?????? ??????
        if(totalCount < 1)
        {
            return userRankHistory;
        }
        else{

            driver.get(BLOG_DEFUALT_URL+userId+LIST_PARM);
            wait.until(ExpectedConditions.presenceOfElementLocated(new By.ByCssSelector("#postlist_block > div.list_block__WkcEG > div > div.list__fJdGM > ul > li")));

            String aMonthBefore = CommonUtil.AddDate(CommonUtil.getyyyyMmddDate(),0,-1,0);

            boolean stop =false;
            long new_height =-1;
            int beforeListSize =0;
            long monthlyPostingCount =0;
            List<String> postingNumbers  = new ArrayList<>();
            while(true)
            {
                long last_height = (long) driver.executeScript("return document.body.scrollHeight");
                if(last_height == new_height)
                {
                    try{
                        Thread.sleep(1000);
                    }catch(Exception e)
                    {
                        log.error("[ERROR]Thread.sleep : {}",e.getMessage());
                    }
                }
                List<WebElement> posting = driver.findElements(new By.ByCssSelector("#postlist_block > div.list_block__WkcEG > div > div.list__fJdGM > ul > div"));

                if(totalCount == beforeListSize)//??? ???????????? 30??? ????????? ??????
                {
                    break;
                }

                for(int i=beforeListSize; i<posting.size(); i++)
                {
                    String pubishDate =posting.get(i).findElement(new By.ByCssSelector("div.desc__VbQBy > span.time__BwQ8u")).getText();
                    pubishDate =pubishDate.replace("\\s","").replace(" ","");//????????????
                    String []yyyyMmdd = pubishDate.split("\\.");
                    if(yyyyMmdd.length ==3)
                    {
                        pubishDate = yyyyMmdd[0];
                        for(int j=1; j<3; j++)
                        {
                            if(yyyyMmdd[j].length() == 1)
                            {
                                pubishDate+="0"+yyyyMmdd[j];
                            }
                            else
                                pubishDate+=yyyyMmdd[j];
                        }
                    }

                    String postingNum = posting.get(i).findElement(new By.ByCssSelector("div.postlist__LXY3R > a" )).getAttribute("href");
                    postingNumbers.add(postingNum.split("\\?")[0].split(BLOG_DEFUALT_URL+userId)[1].replace("/",""));
                    monthlyPostingCount++;
                    //                ????????? ??????
                    //                if(yyyyMmdd.length==3 && pubishDate.compareTo("20220112") < 0)
                    //                {
                    //                    stop = true;
                    //                    postingCountInAMonth = i+1;
                    //                    break;
                    //                }
                    if(i==29)
                    {
                        stop = true;
                        break;
                    }
                }
                beforeListSize = posting.size();
                if(stop)
                    break;
                js.executeScript("window.scrollTo(0, document.body.scrollHeight)");
                new_height = (long) driver.executeScript("return document.body.scrollHeight");

            }

            driver.close();

            Map<String, Integer> tags = new HashMap<>();
            List<String> blogTags = new ArrayList<>();
            for(String postingNum : postingNumbers)
            {
                try{
                    String targetUrl = BLOG_DEFUALT_URL+userId+"/"+postingNum;
                    Document doc = Jsoup.connect(targetUrl).get();
                    Elements elements = doc.select("div.post_tag li");
                    int inputTagCount =0;
                    for(Element element : elements)
                    {
                        String tag = element.select("li span.ell").text().replaceAll("#","");
                        if(tags.get(tag)== null || tags.get(tag) != 1)
                        {
                            tags.put(tag,1);
                            blogTags.add(tag);
                            inputTagCount++;
                        }

                        if(inputTagCount == 5)
                            break;
                    }
                }
                catch(Exception e)
                {
                    System.out.println("[ERROR]JSOUP PARSING : "+e.getMessage());
                }
            }
            double totalScore = 0;

            // ?????? 30?????? ???????????? ????????? ????????? ??? ????????????
            for(String keyword : blogTags)
            {
                //view ??? ????????? ????????????
                List<String> rankerUrls = CommonUtil.getKeywordViewRankers(keyword);
                for(int i=0; i< rankerUrls.size(); i++)
                {
                    String url = rankerUrls.get(i);
                    if(url.contains(userId))//5????????? ?????????
                    {
                        double rankingValue= 5.0/(i+1);
                        int monthlySearchCount = CommonUtil.getMontlySearchMount(keyword).getTotalSearchCount();
                        double searchValue = monthlySearchCount/1000.0;
                        double score = Math.round(rankingValue*searchValue*100)/100.0;
                        totalScore +=score;
                    }
                }
            }
            long tagCount =blogTags.size();
            userRankHistory.setTagCount(tagCount);
            userRankHistory.setMonthPostingCount(monthlyPostingCount);
            userRankHistory.setTotalScore(Math.round(totalScore*100)/100.0);
            return userRankHistory;
        }
    }

    public static int calcWordCount(Elements elements)
    {
        int wordCount =0;
        Elements spans = elements.select("span");
        for(Element e : spans)
        {
            String text = e.text();
            wordCount += text.replaceAll("[^a-zA-Z???-???]", "").length();

        }
        return wordCount;
    }

    public static Elements getBlogMainContents(String url)
    {
        Elements blog_element = null;
        try{
            Document doc = Jsoup.connect(url).get();
            // iframe ????????? ?????? ?????? ????????? ?????? ????????????
            Elements iframes = doc.select("iframe#mainFrame");
            String src = iframes.attr("src");
            //?????? ????????? ?????? document ????????????
            String url2 = "https://blog.naver.com" + src;
            Document doc2 = Jsoup.connect(url2).get();
            // ??????????????? ????????? ????????? ????????? ????????????
            String[] blog_logNo = src.split("&");
            String[] logNo_split = blog_logNo[1].split("=");
            String logNo = logNo_split[1];

            // ????????? ?????? ????????? ?????? ????????????
            String real_blog_addr = "div#post-view" + logNo;
            blog_element = doc2.select(real_blog_addr);
        }
        catch(Exception e)
        {
            System.out.println(e.getMessage());
        }
        return blog_element;
    }

}
