package com.neuedu.utils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.ansj.app.keyword.KeyWordComputer;
import org.ansj.app.keyword.Keyword;
import org.ansj.app.summary.SummaryComputer;
import org.ansj.app.summary.pojo.Summary;
import org.ansj.domain.Result;
import org.ansj.domain.Term;
import org.ansj.splitWord.analysis.IndexAnalysis;
import org.ansj.splitWord.analysis.NlpAnalysis;
import org.ansj.splitWord.analysis.ToAnalysis;
import org.apache.commons.lang3.StringUtils;
import org.nlpcn.commons.lang.jianfan.JianFan;
import org.nlpcn.commons.lang.pinyin.Pinyin;
import org.nlpcn.commons.lang.util.WordAlert;
import org.nlpcn.commons.lang.util.WordWeight;
import org.springframework.stereotype.Component;

/** *  * @author mengyao * */

@Component
public class WordUtil {
    public static void main(String[] args) {
        System.out.println("2016/06/25".matches("^\\d{4}(\\-|\\/|\\.)\\d{1,2}\\1\\d{1,2}$"));
        System.out.println("20160625".matches("^\\d{8}$"));
    }

    /**
     * 文章摘要
     * @param title
     * @param content
     * @return
     */
    public static String getSummary(String title, String content) {
        SummaryComputer summaryComputer = new SummaryComputer(title, content);
        Summary summary = summaryComputer.toSummary();
        return summary.getSummary();
    }

    /**
     * 带标题的文章关键词提取
     * @param title
     * @param content
     * @return
     */
    public static List<Keyword> getKeyWord(String title, String content) {
        List<Keyword> keyWords = new ArrayList<Keyword>();
        KeyWordComputer<NlpAnalysis> kwc = new KeyWordComputer<NlpAnalysis>(20);
        Collection<Keyword> result = kwc.computeArticleTfidf(title, content);
        for (Keyword keyword : result) {
            keyWords.add(keyword);
        }
        return keyWords;
    }

    /**
     * 不带标题的文章关键词提取
     * @param content
     * @return
     */
    public static List<Keyword> getKeyWord2(String content) {
        List<Keyword> keyWords = new ArrayList<Keyword>();
        KeyWordComputer<NlpAnalysis> kwc = new KeyWordComputer<NlpAnalysis>(20);
        Collection<Keyword> result = kwc.computeArticleTfidf(content);
        for (Keyword keyword : result) {
            keyWords.add(keyword);
        }
        return keyWords;
    }

    /**
     * 标准分词
     * @param text
     * @return
     */
    public static List<Term> getToSeg(String text) {
        List<Term> words = new ArrayList<Term>();
        Result parse = ToAnalysis.parse(text);
        for (Term term : parse) {
            if (null!=term.getName()&&!term.getName().trim().isEmpty()) {
                words.add(term);
            }
        }
        return words;
    }

    /**
     * NLP分词
     * @param text
     * @return
     */
    public static List<Term> getNlpSeg(String text) {
        List<Term> words = new ArrayList<Term>();
        Result parse = NlpAnalysis.parse(text);
        for (Term term : parse) {
            if (null!=term.getName()&&!term.getName().trim().isEmpty()) {
                words.add(term);
            }
        }
        return words;
    }

    /**
     * Index分词
     * @param text
     * @return
     */
    public static List<Term> getIndexSeg(String text) {
        List<Term> words = new ArrayList<Term>();
        Result parse = IndexAnalysis.parse(text);
        for (Term term : parse) {
            if (null!=term.getName()&&!term.getName().trim().isEmpty()) {
                words.add(term);
            }
        }
        return words;
    }

    /**
     * 简体转繁体
     * @param word
     * @return
     */
    public static String jian2fan(String text) {
        return JianFan.j2f(text);
    }

    /**
     * 繁体转简体
     * @param word
     * @return
     */
    public static String fan2jian(String text) {
        return JianFan.f2j(text);
    }

    /**
     * 拼音(不带音标)
     * @param word
     * @return
     */
    public static String pinyin(String text) {
        StringBuilder builder = new StringBuilder();
        List<String> pinyins = Pinyin.pinyin(text);
        for (String pinyin : pinyins) {
            if (null != pinyin) {
                builder.append(pinyin+" ");
            }
        }
        return builder.toString();
    }

    /**
     * 拼音(不带音标，首字母大写)
     * @param word
     * @return
     */
    public static String pinyinUp(String text) {
        StringBuilder builder = new StringBuilder();
        List<String> pinyins = Pinyin.pinyin(text);
        for (String pinyin : pinyins) {
            if (StringUtils.isEmpty(pinyin)) {
                continue;
            }
            builder.append(pinyin.substring(0,1).toUpperCase()+pinyin.substring(1));
        }
        return builder.toString();
    }

    /**
     * 拼音(带数字音标)
     * @param word
     * @return
     */
    public static String tonePinyin(String text) {
        StringBuilder builder = new StringBuilder();
        List<String> pinyins = Pinyin.tonePinyin(text);
        for (String pinyin : pinyins) {
            if (null != pinyin) {
                builder.append(pinyin+" ");
            }
        }
        return builder.toString();
    }

    /**
     * 拼音(带符号音标)
     * @param word
     * @return
     */
    public static String unicodePinyin(String text) {
        StringBuilder builder = new StringBuilder();
        List<String> pinyins = Pinyin.unicodePinyin(text);
        for (String pinyin : pinyins) {
            if (null != pinyin) {
                builder.append(pinyin+" ");
            }
        }
        return builder.toString();
    }

    /**
     * 词频统计
     * @param words
     * @return
     */
    public static Map<String, Double> wordCount(List<String> words) {
        WordWeight ww = new WordWeight();
        for (String word : words) {
            ww.add(word);
        }
        return ww.export();
    }

    /**
     * 词频统计
     * @param words
     * @return
     */
    public static List<String> wordCount1(List<String> words) {
        List<String> wcs = new ArrayList<String>();
        WordWeight ww = new WordWeight();
        for (String word : words) {
            ww.add(word);
        }
        Map<String, Double> export = ww.export();
        for (Entry<String, Double> entry : export.entrySet()) {
            wcs.add(entry.getKey()+":"+entry.getValue());
        }
        return wcs;
    }

    /**
     * 语种识别:1英文；0中文
     * @param words
     * @return
     */
    public static int language(String word) {
        return WordAlert.isEnglish(word)?1:0;
    }

}