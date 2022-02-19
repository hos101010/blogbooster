package com.snl.blogbooster.service;

import com.snl.blogbooster.mapper.PostingMapper;
import com.snl.blogbooster.model.domain.Posting;
import com.snl.blogbooster.model.domain.Tag;
import com.snl.blogbooster.model.domain.Video;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONArray;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Objects;

@Slf4j
@Service
@RequiredArgsConstructor
public class PostingService {

    private final PostingMapper postingMapper;
    private final VideoService videoService;
    private final TagService tagService;

    public Elements getBlogMainContents(String url)
    {
        Elements blog_element = null;
        try{
            Document doc = Jsoup.connect(url).get();
            // iframe 태그에 있는 진짜 블로그 주소 가져오기
            Elements iframes = doc.select("iframe#mainFrame");
            String src = iframes.attr("src");
            //진짜 블로그 주소 document 가져오기
            String url2 = "https://blog.naver.com" + src;
            Document doc2 = Jsoup.connect(url2).get();
            // 블로그에서 원하는 블로그 페이지 가져오기
            String[] blog_logNo = src.split("&");
            String[] logNo_split = blog_logNo[1].split("=");
            String logNo = logNo_split[1];

            // 찾고자 하는 블로그 본문 가져오기
            String real_blog_addr = "div#post-view" + logNo;
            blog_element = doc2.select(real_blog_addr);
        }
        catch(Exception e)
        {
            System.out.println(e.getMessage());
        }
        return blog_element;
    }

    public void insertPosting(String url,String requestUserId)
    {
        String registerUserId = null;
        long postingNum =-1;
        if(url.contains("blog.naver.com"))
        {
            String []urlArray = url.split("blog.naver.com");
            registerUserId = urlArray[urlArray.length-1].split("/")[1];
            postingNum = Long.parseLong(urlArray[urlArray.length-1].split("/")[2]);
        }

        log.info("registerUserId : {}, postingNum : {}",registerUserId,postingNum );
        if(registerUserId == null || postingNum < 0)
        {
            //잘못된 url
            return ;
        }
        else if(!registerUserId.equals(requestUserId)){
            //등록자와 요청자가 다를경우에만 requestHistory에 넣음
            postingMapper.insertRequestHistory(url,requestUserId);
        }

        Elements blogMainContents = getBlogMainContents(url);
        String title =blogMainContents.select("div[class=\"se-module se-module-text se-title-text\"]").select("span").text();
        Elements images = blogMainContents.select("img");
        int imageCount =0;
        for(Element e : images)
        {   String image = e.attr("data-lazy-src");
            if(image.contains("postfiles"))
                imageCount ++;// 본문에 사용한 image 개수 가져오기
        }
        Elements videos = blogMainContents.select("div[class=\"se-component se-video se-l-default\"]");
        int videoCount =videos.size();

        int wordCount = calcWordCount(blogMainContents);

        Posting posting = Posting.builder()
                .postingNum(postingNum)
                .imageCount(imageCount)
                .title(title)
                .videoCount(videoCount)
                .wordCount(wordCount)
                .registerUserId(registerUserId)
                .url(url).build();
        postingMapper.insertPosting(posting);
        videoService.deleteVideo(postingNum);

        for(Element element : videos)
        {
            String videoJsonInfo = element.select("script").attr("data-module");
            JSONObject jsonObject = new JSONObject(videoJsonInfo);
            JSONObject jsonData = jsonObject.getJSONObject("data");
            JSONObject jsonMetaData = jsonData.getJSONObject("mediaMeta");


            String videoTitle = "";
            Object titleObj = jsonMetaData.get("title");
            if(titleObj != null)
            {
                videoTitle = titleObj.toString();
            }
            String description="";
            Object decriptionObj = jsonMetaData.get("description");
            if(!decriptionObj.toString().equals("null"))
            {
                description = decriptionObj.toString();
            }
            JSONArray tags = null;
            if(jsonMetaData.get("tags") != null)
            {
                tags = jsonMetaData.getJSONArray("tags");
            }
            Video video = Video.builder()
                    .postingNum(postingNum)
                    .title(videoTitle)
                    .description(description).build();
            videoService.insertVideo(video);
            long videoNum = video.getVideoNum();
            log.info("videoNum : {}",videoNum);
            for(int i = 0; i< Objects.requireNonNull(tags).length(); i++)
            {
                String content = (String)tags.get(i);
                Tag tag = Tag.builder()
                        .content(content)
                        .itemNum(videoNum)
                        .itemType("VIDEO")
                        .build();
                tagService.insertTag(tag);
            }
        }
    }

    public int calcWordCount(Elements elements)
    {
        int wordCount =0;
        Elements spans = elements.select("span");
        for(Element e : spans)
        {
            String text = e.text();
            wordCount += text.replaceAll("[^a-zA-Zㄱ-힣]", "").length();

        }
        return wordCount;
    }

}
