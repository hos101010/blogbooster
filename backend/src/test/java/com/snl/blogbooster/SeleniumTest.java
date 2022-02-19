package com.snl.blogbooster;

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

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SeleniumTest {

    private static class POST{
        private String title;
        private String url;
    };

    private static class BLOGER {

        private String category;
        private int totalPostingCount;
        private int postingCountInAMonth;
        private int dayVisitor;
        private int totalVisitor;
        private int neighborCount;
        private List<POST> postings;
    };

    private static String URL="https://m.blog.naver.com/ckrzkssja123";
    private static String LIST_PARM = "?categoryNo=0&listStyle=post";
    private static ChromeDriver driver;
    public static void main(String[] args) throws IOException, InterruptedException {
        System.setProperty("webdriver.chrome.driver", "C://Users//혀비스//Desktop//chromedriver_win32//chromedriver.exe");
        // 2. WebDriver 옵션 설정
        ChromeOptions opt = new ChromeOptions();
        opt.addArguments("headless");         //브라우저의 모습을 안띄우겟다.
        opt.addArguments("--blink-settings=imagesEnabled=false"); //이미지 다운 안받을거야.
        driver = new ChromeDriver(opt);

        WebDriverWait wait = new WebDriverWait(driver,10);
        JavascriptExecutor js = (JavascriptExecutor) driver;

        // Launch the application
        driver.get(URL);

        String visitorCount = driver.findElement(new By.ByCssSelector("#root > div.blog_cover__Il6gZ > div > div.title_area__W05iJ > div")).getText();
        String blogThema  = driver.findElement(new By.ByCssSelector("#root > div.blog_cover__Il6gZ > div > div.bloger_area___GicX > div.text_area___K6nq > div.bloger_info__KrWs1 > span.subject__CBtO5")).getText();
        String neborCount = driver.findElement(new  By.ByCssSelector("#root > div.blog_cover__Il6gZ > div > div.bloger_area___GicX > div.text_area___K6nq > div.bloger_info__KrWs1 > span.buddy___ckI_")).getText();

        //move to category page
        wait.until(ExpectedConditions.presenceOfElementLocated(new By.ByCssSelector("#root > div.blog_cover__Il6gZ > div > div.btn_area__OtwBw > div:nth-child(2) > button")));
        WebElement el = driver.findElement(new By.ByCssSelector("#root > div.blog_cover__Il6gZ > div > div.btn_area__OtwBw > div:nth-child(2) > button"));
        el.click();//카테고리 버튼 찾아서 클릭

        //이동한 페이지에서 총 발행수 가져오기
        wait.until(ExpectedConditions.presenceOfElementLocated(new By.ByCssSelector("#root > div.apollo_layer_container > div > div > div > ul > li > a > div > em")));
        WebElement totalPublication = driver.findElement(new By.ByCssSelector("#root > div.apollo_layer_container > div > div > div > ul > li > a > div > em"));
        String totalCount = totalPublication.getText();

        //목록형으로 이동
        driver.get(URL+LIST_PARM);
        wait.until(ExpectedConditions.presenceOfElementLocated(new By.ByCssSelector("#postlist_block > div.list_block__WkcEG > div > div.list__fJdGM > ul > li")));


        boolean stop =false;
        long new_height =-1;
        int beforeListSize =0;
        int postingCountInAMonth =0;
        List<POST> posts  = new ArrayList<>();
        while(true)
        {
            long last_height = (long) driver.executeScript("return document.body.scrollHeight");
            if(last_height == new_height)
            {
                Thread.sleep(1000);
            }
            List<WebElement> posting = driver.findElements(new By.ByCssSelector("#postlist_block > div.list_block__WkcEG > div > div.list__fJdGM > ul > div"));
            for(int i=beforeListSize; i<posting.size(); i++)
            {
                String pubishDate =posting.get(i).findElement(new By.ByCssSelector("div.desc__VbQBy > span.time__BwQ8u")).getText();
                pubishDate =pubishDate.replace("\\s","").replace(" ","");//공백제거
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
                String title = posting.get(i).findElement(new By.ByCssSelector("strong.title__yuIvy > span.ell2 > span")).getText();
                String postUrl = posting.get(i).findElement(new By.ByCssSelector("div.postlist__LXY3R > a" )).getAttribute("href");
                POST post = new POST();
                post.title =title;
                post.url = postUrl.split("\\?")[0].split(URL)[1].replace("/","");
                posts.add(post);

//                한달간 측정
//                if(yyyyMmdd.length==3 && pubishDate.compareTo("20220112") < 0)
//                {
//                    stop = true;
//                    postingCountInAMonth = i+1;
//                    break;
//                }
                if(i==29)
                {
                    stop = true;
                    postingCountInAMonth = i+1;
                    break;
                }
            }
            beforeListSize = posting.size();
             if(stop)
                 break;
             js.executeScript("window.scrollTo(0, document.body.scrollHeight)");
            new_height = (long) driver.executeScript("return document.body.scrollHeight");

        }

        //1달이 넘어가는 날짜에 쓴 글이 나올때까지 스크롤 다운

        //Calculate Publication count per month
        String url  = driver.getCurrentUrl();
        System.out.println("totalCount : "+Integer.parseInt(totalCount.replaceAll(",","")));
        String [] array = visitorCount.split("\\s");
        System.out.println("visitorTodayCount : "+Integer.parseInt(array[1].replaceAll(",","")));
        System.out.println("visitorTotalCount : "+Integer.parseInt(array[3].replaceAll(",","")));
        System.out.println("blogThema : "+blogThema);
        System.out.println("postingCountInAMonth : "+postingCountInAMonth);
        System.out.println("neborCount : "+Integer.parseInt(neborCount.replaceAll("명의 이웃","").replaceAll(",","")));

        BLOGER post = new BLOGER();
        post.category=blogThema;
        post.postings = posts;
        post.totalPostingCount=Integer.parseInt(totalCount.replaceAll(",",""));
        post.dayVisitor=Integer.parseInt(array[1].replaceAll(",",""));
        post.totalVisitor=Integer.parseInt(array[3].replaceAll(",",""));
        post.postingCountInAMonth =postingCountInAMonth;
        post.neighborCount=Integer.parseInt(neborCount.replaceAll("명의 이웃","").replaceAll(",",""));
        driver.close();

        Map<String,Integer> tags = new HashMap<>();
        for(POST post1 : posts)
        {
            String postingNum = post1.url;
            try{
                String targetUrl = "https://m.blog.naver.com/ckrzkssja123/"+postingNum;
                Document doc = Jsoup.connect(targetUrl).get();
                Elements elements = doc.select("div.post_tag li");
                for(Element element : elements)
                {
                    String tag = element.select("li span.ell").text().replaceAll("#","");
//                    System.out.println(tag);
                    if(tags.get(tag)== null || tags.get(tag) != 1)
                    {
                        tags.put(tag,1);
                    }
                }
            }
            catch(Exception e)
            {
                System.out.println("[ERROR]JSOUP PARSING : "+e.getMessage());
            }
        }
        System.out.println("size : "+tags.size());
        for(Map.Entry entry : tags.entrySet())
        {
            System.out.println(entry.getKey());
        }
    }
}
