package com.snl.blogbooster;

import com.snl.blogbooster.model.dto.InfluenceRequestDto;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootTest
@AutoConfigureMockMvc
@EnableScheduling
public class BlogUserTest {


    private final static String INFLUENCE_URL ="https://m.blog.naver.com/";
    public static void main(String[] args) {
        blogUserTest();
    }

    public static void blogUserTest()
    {
        String blogId ="phjsunflower";
            try{
                Document doc = Jsoup.connect("https://m.blog.naver.com/phjsunflower/222540664348").get();
                System.out.println(doc);
                Elements elements = doc.select("div.post_tag li");
/*                for(Element element : elements)
                {
                    System.out.println(element.select("li span.ell").text());
                }*/
                for(int i =0; i<elements.size(); i++)
                {
                    System.out.println(elements.get(i).select("a span.ell").text().replaceAll("#",""));
                }
            }
            catch(Exception e)
            {
            }
        }
}
