package com.snl.blogbooster;

import com.snl.blogbooster.mapper.InfluenceMapper;
import com.snl.blogbooster.model.dto.InfluenceRequestDto;
import com.snl.blogbooster.service.WordService;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.scheduling.annotation.EnableScheduling;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest
@AutoConfigureMockMvc
@EnableScheduling
public class InfluenceTest {

    private static InfluenceMapper influenceMapper;
    private final static String [] INFLUENCE_KEYWORD={
                                                   "노랑통닭","라무진","굽네치킨","네네치킨","지코바"
                                                    ,"샐러디","아와비","이삭토스트","피자헛","파파존스"
                                                    ,"롯데리아","푸라닭","설빙","육쌈냉면","오마카세"
                                                    ,"이차돌","던킨도너츠","후참잘","소담촌","서가앤쿡"
                                                    ,"한신포차","배스킨라빈스","배라","파리바게트","징기스"
                                                    ,"촌놈집","비비큐","누데이크","후토마끼","서브웨이"
                                                    ,"하남돼지집","밀면","서브웨이","은행골","또래오래"
                                                    ,"편백찜","아비꼬","스테이크하우스","모소리","교촌치킨"
                                                    ,"홍루이젠","BHC치킨","호식이두마리치킨","골드킹","노티드"
                                                    ,"연화리","육미안","힙지로","페리카나","꼬들목"
                                                    ,"처갓집","더현대","츠츠허허","배꼽집","양평해장국"
                                                    ,"대방어"
    };
    private final static String INFLUENCE_URL ="https://search.naver.com/search.naver?where=influencer&sm=tab_jum&query=";
    public static void main(String[] args) {
        influenceTest();
    }

    public static void influenceTest()
    {
        for(String keyword : INFLUENCE_KEYWORD)
        {
            try{
                Document doc = Jsoup.connect(INFLUENCE_URL+keyword).get();
                Elements viewTab = doc.select("ul.keyword_challenge_list._inf_contents");
//                Elements names = viewTab.select("div.user_area a span.txt");
                Elements influenceInfo = viewTab.select("div.keyword_box_wrap.api_ani_send.type_color");
                Elements elements = influenceInfo.select("div.user_area a span.txt");
//                Elements elements1 = influenceInfo.select("div.detail_box span.date");
                Elements elements1 = influenceInfo.select("div.dsc_area");
                boolean isInclude = false;
                for(int i=0; i<3; i++)
                {
                    String rankerName = elements.get(i).text();
                    if("먹덕후".equals(rankerName))
                    {
                        isInclude = true;
                        break;
                    }
                }
                String ranking ="20위 밖";
                String date ="알수없음";
                for(int i=3; i<elements.size(); i++)
                {
                    String rankerName = elements.get(i).text();
                    if("먹덕후".equals(rankerName))
                    {
                        ranking = String.valueOf(i+1) ;
                        date = elements1.get(i).select("span.date").text();
                        break;
                    }
                }
                if(!isInclude)
                {
                    InfluenceRequestDto influenceRequestDto = InfluenceRequestDto.builder()
                            .ranking(ranking)
                            .keyword(keyword)
                            .publishDate(date)
                            .build();
                    System.out.println("keyword : "+keyword);
                    influenceMapper.insertInflKeyword(influenceRequestDto);
                }
            }
            catch(Exception e)
            {
            }
        }
    }
}
