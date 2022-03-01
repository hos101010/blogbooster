package com.snl.blogbooster.service;

import com.snl.blogbooster.common.CommonUtil;
import com.snl.blogbooster.model.domain.posting.Posting;
import com.snl.blogbooster.model.domain.posting.PostingRepository;
import com.snl.blogbooster.model.domain.tag.Tag;
import com.snl.blogbooster.model.domain.tag.TagRepository;
import com.snl.blogbooster.model.domain.video.Video;
import com.snl.blogbooster.model.domain.video.VideoRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONArray;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Slf4j
@Service
@RequiredArgsConstructor
public class PostingService {

    private final PostingRepository postingRepository;
    private final TagRepository tagRepository;
    private final VideoRepository videoRepository;

    @Transactional
    public Posting findByUrl(String url)
    {
        String []urlArray = url.split("m.blog.naver.com");
        long postingNum = Long.parseLong(urlArray[urlArray.length-1].split("/")[2]);
        return postingRepository.findPostingByPostingNum(postingNum);
    }

    @Transactional
    public Posting save(String url){

        Posting posting = null;

        //파라미터로 전달받을 URL
        //url ="https://m.blog.naver.com/ckrzkssja123/222644696063";
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
            return new Posting();
        }

        /* PostingNum으로 태그, 비디오 테이블 delete */
        tagRepository.deleteAlByPostingNum(postingNum);
        videoRepository.deleteAllByPostingNum(postingNum);

        /* 제목가져오기*/
        try {
            Document document = Jsoup.connect(url).get();
            String title = document.select("div.se-title-text span").text();

            /*등록한 날짜 가져오기*/
            String postDate = document.select("div.blog_authorArea p.blog_date").text();

            /*첨부된 이미지 수 가져오기*/
            Elements images = document.select("div.se-main-container img");
            int imageCount = 0;
            for (Element e : images) {
                String image = e.attr("src");
                if (image.contains(registerUserId))
                    imageCount++;// 본문에 사용한 image 개수 가져오기
            }


            /*본문 글자수 가져오기*/
            int wordCount = CommonUtil.calcWordCount(document.select("div.se-main-container"));

            /*비디오정보 가져오기*/
            Elements videos = document.select("div[class=\"se-component se-video se-l-default\"]");
            int videoCount = videos.size();

            posting = Posting.builder()
                    .postingNum(postingNum)
                    .imageCount(imageCount)
                    .postDate(postDate)
                    .title(title)
                    .videoCount(videoCount)
                    .wordCount(wordCount)
                    .registerUserId(registerUserId)
                    .url(url).build();
            postingRepository.save(posting);

            /*메인에 추가된 태그가져오기*/
            Map<String, Integer> tagMaps = new HashMap<>();
            Set<Tag> blogTags = new HashSet<>();
            Elements postingTags = document.select("div.post_tag li");
            for (Element element : postingTags) {
                String tag = element.select("li span.ell").text().replaceAll("#", "");
                if (tagMaps.get(tag) == null || tagMaps.get(tag) != 1) {
                    Tag mainTag = Tag.builder()
                            .content(tag)
                            .tagType("MAIN")
                            .postingNum(postingNum)
                            .posting(posting)
                            .build();
                    tagMaps.put(tag, 1);
                    blogTags.add(mainTag);
                }
            }
            tagRepository.saveAll(blogTags);
            posting.setTags(blogTags);

            /*비디오 정보 가져오기*/
            Set<Video> videoList = new HashSet<>();
            for (Element element : videos) {
                String videoJsonInfo = element.select("script").attr("data-module");
                JSONObject jsonObject = new JSONObject(videoJsonInfo);
                JSONObject jsonData = jsonObject.getJSONObject("data");
                JSONObject jsonMetaData = jsonData.getJSONObject("mediaMeta");


                String videoTitle = "";
                Object titleObj = jsonMetaData.get("title");
                if (titleObj != null) {
                    videoTitle = titleObj.toString();
                }
                String description = "";
                Object decriptionObj = jsonMetaData.get("description");
                if (!decriptionObj.toString().equals("null")) {
                    description = decriptionObj.toString();
                }
                JSONArray tags = null;
                if (jsonMetaData.get("tags") != null) {
                    tags = jsonMetaData.getJSONArray("tags");
                }
                Video video = Video.builder()
                        .postingNum(postingNum)
                        .title(videoTitle)
                        .description(description).build();
                videoRepository.save(video);
                long videoNum = video.getVideoNum();
                Set<Tag> videoTags = new HashSet<>();
                for (int i = 0; i < Objects.requireNonNull(tags).length(); i++) {
                    String content = (String) tags.get(i);
                    Tag tag = Tag.builder()
                            .posting(posting)
                            .postingNum(postingNum)
                            .content(content)
                            .videoNum(videoNum)
                            .tagType("VIDEO")
                            .build();
                    tagRepository.save(tag);
                    videoTags.add(tag);
                }
                video.setTags(videoTags);
                videoList.add(video);
            }
            posting.setVideos(videoList);

        }catch(Exception e)
        {
        }
        return posting;
    }
}
