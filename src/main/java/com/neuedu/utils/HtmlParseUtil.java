package com.neuedu.utils;

import com.neuedu.pojo.Content;
import com.neuedu.pojo.Poem;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Component;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

@Component
public class HtmlParseUtil {//Parse 解析

    //url不能是中文，否则要重载一下
    public List<Content> parseJD(String keywords) throws Exception{
        List<Content> list = new ArrayList<>(  );
        //获取请求 https://search.jd.com/Search?keyword=java
        //前提需要联网 ajax不能获取，需要模拟浏览器
        //String url = "https://search.jd.com/Search?keyword=java";
        String url = "https://search.jd.com/Search?keyword=" + keywords;
        //解析网页(jsoup返回的就是浏览器的document对象）
        Document docu = Jsoup.parse( new URL( url ),30000 );
        //所有在js中使用的方法都能用
        Element element = docu.getElementById( "J_goodsList" );
        //System.out.println(element.html());
        //获取所有的li标签
        Elements element1 = element.getElementsByTag("li");
        //System.out.println(element1.html());
        //获取元素中的内容,e是每个li标签
        for(Element e : element1){
            //大多数网站图片都是懒加载（延迟加载），为了提高加载性能，source-data-lazy-img,不是img
            String img = e.getElementsByTag( "img" ).eq(0).attr( "data-lazy-img" );
            String price = e.getElementsByClass( "p-price" ).eq(0).text();
            String title = e.getElementsByClass( "p-name" ).eq(0).text();

//            System.out.println("==================");
//            System.out.println(img);
//            System.out.println(price);
//            System.out.println(title);
            Content content = new Content( title,price,img );
            list.add( content );
        }
        return list;
    }

    public Poem parseSW(String keywords) throws Exception {
        String title;
        String poet;
        String context;
        String transformation;
        String evaluate;
        String time;
        Poem poem;

        String url = "http://www.haoshiwen.org/" + parsePomeUrl( keywords );
        Document document = Jsoup.parse( new URL( url ), 30000 );
        Element shileft = document.getElementsByClass( "shileft" ).get( 0 );

        Element elementTitle = shileft.getElementsByTag( "h1" ).get( 0 );
        Element elementTime = shileft.getElementsByTag( "p" ).get( 0 );
        Element elementPoet = shileft.getElementsByTag( "p" ).get( 1 );
        Element elementContext = shileft.getElementsByTag( "p" ).get( 3 );
        Element elementTransformation = shileft.getElementsByClass( "son5" ).get( 1 ).getElementsByTag( "p" ).get( 1 );
        Element elementEvaluate = shileft.getElementsByClass( "son5" ).get( 2 ).getElementsByTag( "p" ).get( 1 );

        //System.out.println(shileft);
        title = elementTitle.ownText();
        time = elementTime.ownText();
        poet = elementPoet.children().get( 1 ).text();
        context = elementContext.ownText();
        transformation = elementTransformation.ownText();
        evaluate = elementEvaluate.ownText();

        //System.out.println(evaluate);
        poem = new Poem( title,poet,context,transformation,evaluate,time );

        return poem;
    }

    public static void main(String[] args) throws Exception {
        //new HtmlParseUtil().parseJD( "java" ).forEach( System.out::println );
        //new HtmlParseUtil().parseSW( "九月九日忆山东兄弟" ).forEach( System.out::println );
        Poem poem = new HtmlParseUtil().parseSW( "问刘十九" );
        System.out.println(poem);
    }

    private String parsePomeUrl(String keywords) throws Exception {
        //http://www.haoshiwen.org/search.php?txtKey=%E9%9D%99%E5%A4%9C%E6%80%9D
        String url = "http://www.haoshiwen.org/search.php?txtKey=" + keywords;
        Document document = Jsoup.parse( new URL( url ), 30000 );
        Element sons = document.getElementsByClass( "sons" ).get( 0 );
        Element a = sons.getElementsByTag( "a" ).get( 1 );
        String href = a.attr( "href" );
        //System.out.println(href);
        return href;
    }
}
