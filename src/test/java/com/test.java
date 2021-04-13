package com;

import com.hankcs.hanlp.HanLP;
import com.neuedu.utils.WordUtil;
import org.junit.jupiter.api.Test;



public class test {

    /**
     * 测试分离诗词
     * */
    @Test
    public void test01(){
        String str = "旅途在青山外，在碧绿的江水前行舟。潮水涨满，两岸之间水面宽阔，顺风行船恰好把帆儿高悬。夜幕还没有褪尽，旭日已在江上冉冉升起，还在旧年时分，江南已有了春天的气息。寄出去的家信不知何时才能到达，希望北归的大雁捎到洛阳去。";
        String porm = "朝辞白帝彩云间,千里江陵一日还.两岸猿声啼不住,轻舟已过万重山.";
        WordUtil wordUtil = new WordUtil();
        System.out.println(wordUtil.getToSeg( porm ));
    }

    @Test
    public void test02(){
        HanLP.parseDependency("2021年HanLPv2.1为生产环境带来次世代最先进的多语种NLP技术。阿婆主来到北京立方庭参观自然语义科技公司。");
    }

    public static void main(String[] args) {
        String title = "欢迎";
        String str = "欢迎使用ansj_seg,(ansj中文分词)在这里如果你遇到什么问题都可以联系我.我一定尽我所能.帮助大家.ansj_seg更快,更准,更自由!" ;
        WordUtil wordUtil = new WordUtil();
        System.out.println(wordUtil.pinyin( str ));
        //System.out.println(wordUtil.jian2fan( str ));
        //System.out.println(wordUtil.getSummary( title,str ));
        //System.out.println(wordUtil.getKeyWord( title,str ));
        //System.out.println(wordUtil.getKeyWord2( str ));
        System.out.println(wordUtil.getToSeg( str ));
        //System.out.println(TAnalysois.parse(str));
    }
}
