package com.snl.blogbooster.util;

import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

public class EncoderTest {

    //api : https://blog.naver.com/BlogTagListInfo.naver?blogId=phjsunflower&logNoList=222657935302&logType=mylog
    public static void main(String[] args) {
        String encoderWord = "%EB%AC%B8%EB%9E%98%EB%8F%99%EB%A7%9B%EC%A7%91%2C%EB%8F%99%EA%B2%BD%ED%99%94%EB%A1%9C%2C%EA%BD%83%EC%94%A8%EC%9D%98%EB%A7%9B%EC%A7%91%EC%9D%B4%EC%95%BC%EA%B8%B0" +
                "";
//        String text = URLEncoder.encode(keyword, StandardCharsets.UTF_8);

        String text = URLDecoder.decode(encoderWord,StandardCharsets.UTF_8);
        System.out.println(text);
    }
}
