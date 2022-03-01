package com.snl.blogbooster.service;

import com.snl.blogbooster.common.CommonUtil;
import com.snl.blogbooster.model.domain.posting.Posting;
import com.snl.blogbooster.model.domain.posting.PostingRepository;
import com.snl.blogbooster.model.domain.tag.Tag;
import com.snl.blogbooster.model.domain.tag.TagRepository;
import com.snl.blogbooster.model.domain.video.Video;
import com.snl.blogbooster.model.domain.video.VideoRepository;
import org.json.JSONArray;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.util.*;

@SpringBootTest
public class PostingServiceTest {

    @Autowired
    PostingRepository postingRepository;

    @Autowired
    VideoRepository videoRepository;

    @Autowired
    TagRepository tagRepository;

    @Test
    @DisplayName("POSTING 테이블 OneToMany")
    void 일대다조인하기()
    {
        Posting posting = postingRepository.findPostingByPostingNum(222644696063L);
    }

    @Test
    @DisplayName("입력한 URL로 포스팅 정보 가져오기")
    void 포스팅정보가져오기() throws IOException {

        //파라미터로 전달받을 URL
        String url ="https://m.blog.naver.com/ckrzkssja123/222644696063";
        String registerUserId =null;

        /* 유효한 URL인지 확인 */
        long postingNum =-1;
        if(url.contains("blog.naver.com"))
        {
            String []urlArray = url.split("m.blog.naver.com");
            registerUserId = urlArray[urlArray.length-1].split("/")[1];
            postingNum = Long.parseLong(urlArray[urlArray.length-1].split("/")[2]);
        }
        if(registerUserId == null || postingNum < 0)
        {
            //잘못된 url
            return ;
        }

        /* PostingNum으로 태그, 비디오 테이블 claen */
         tagRepository.deleteAlByPostingNum(postingNum);
        videoRepository.deleteAllByPostingNum(postingNum);

        /* 제목가져오기*/
        Document document = Jsoup.connect(url).get();
        String title =document.select("div.se-title-text span").text();


        /*첨부된 이미지 수 가져오기*/
        Elements images = document.select("div.se-main-container img");
        int imageCount =0;
        for(Element e : images)
        {
            String image = e.attr("src");
            if(image.contains(registerUserId))
                imageCount ++;// 본문에 사용한 image 개수 가져오기
        }


        /*본문 글자수 가져오기*/
        int wordCount = CommonUtil.calcWordCount(document.select("div.se-main-container"));

        /*비디오정보 가져오기*/
        Elements videos = document.select("div[class=\"se-component se-video se-l-default\"]");
        int videoCount =videos.size();

        Posting posting = Posting.builder()
                .postingNum(postingNum)
                .imageCount(imageCount)
                .title(title)
                .videoCount(videoCount)
                .wordCount(wordCount)
                .registerUserId(registerUserId)
                .url(url).build();
        postingRepository.save(posting);

        /*메인에 추가된 태그가져오기*/
        Map<String, Integer> tagMaps = new HashMap<>();
        List<Tag> blogTags = new ArrayList<>();
        Elements postingTags =  document.select("div.post_tag li");
        for(Element element : postingTags)
        {
            String tag = element.select("li span.ell").text().replaceAll("#","");
            if(tagMaps.get(tag)== null || tagMaps.get(tag) != 1)
            {
                Tag mainTag = Tag.builder()
                        .content(tag)
                        .tagType("MAIN")
                        .postingNum(postingNum)
                        .posting(posting)
                        .build();
                tagMaps.put(tag,1);
                blogTags.add(mainTag);
            }
        }
        tagRepository.saveAll(blogTags);

        /*비디오 정보 가져오기*/
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
                    .posting(posting)
                    .title(videoTitle)
                    .description(description).build();
            videoRepository.save(video);
            long videoNum = video.getVideoNum();
            for(int i = 0; i< Objects.requireNonNull(tags).length(); i++)
            {
                String content = (String)tags.get(i);
                Tag tag = Tag.builder()
                        .video(video)
                        .content(content)
                        .videoNum(videoNum)
                        .tagType("VIDEO")
                        .build();
                tagRepository.save(tag);
            }
        }
    }

}
