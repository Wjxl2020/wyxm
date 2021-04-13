package com.neuedu;


import com.hankcs.hanlp.classification.classifiers.IClassifier;
import com.hankcs.hanlp.classification.classifiers.NaiveBayesClassifier;
import com.hankcs.hanlp.classification.models.NaiveBayesModel;
import com.hankcs.hanlp.corpus.io.IOUtil;

import java.io.File;
import java.io.IOException;

/**
 * @Author Swallow
 * @Date 2019/4/20
 **/
public class GetModel {
    /**
     * 搜狗文本分类语料库5个类目，每个类目下1000篇文章，共计5000篇文章
     */
    //public static final String CORPUS_FOLDER = TestUtility.ensureTestData("JDComments", "D:\\ws\\ij-workspace\\spring-boot-06-data-jdbc\\data\\test\\JDComments\\JDComments.zip");
    public static final String CORPUS_FOLDER = "D:\\ws\\ij-workspace\\spring-boot-06-data-jdbc\\data\\test\\JDComments\\JDComments.zip";
    /**
     * 模型保存路径
     */
    public static final String MODEL_PATH = "D:\\ws\\ij-workspace\\spring-boot-06-data-jdbc\\model\\classification-model.ser";


    public static void main(String[] args) throws IOException {
        //NaiveBayesModel naiveBayesModel = new NaiveBayesModel();
        IClassifier classifier = new NaiveBayesClassifier(trainOrLoadModel());
        predict(classifier, "用起来感觉很好");
        predict(classifier, "电池很容易没电，拿在手上挺沉的，其他一般般吧");
        predict(classifier, "起初很抵触刘海屏，到现在用习惯了也无所谓，以前用的魅族，用腻了想换个口味。所以，入手了米8。特意用了几天过来评价，系统很流畅拍照也不错，续航就有点扎心了3000毫安时的电池?，总的来说不错的一部机子。\n" +
                "很不错哟，指纹解锁和人脸解锁都超级灵敏");
        predict(classifier, "如果真想用食物解压,建议可以食用燕麦");
        predict(classifier, "通用及其部分竞争对手目前正在考虑解决库存问题");
    }

    private static void predict(IClassifier classifier, String text) {
        System.out.printf("《%s》 属于分类 【%s】\n", text, classifier.classify(text));
    }

    private static NaiveBayesModel trainOrLoadModel() throws IOException {
        NaiveBayesModel model = (NaiveBayesModel) IOUtil.readObjectFrom(MODEL_PATH);
        if (model != null) return model;

        File corpusFolder = new File(CORPUS_FOLDER);
        if (!corpusFolder.exists() || !corpusFolder.isDirectory()) {
            System.err.println("没有文本分类语料，请阅读IClassifier.train(java.lang.String)中定义的语料格式与语料下载：" +
                    "https://github.com/hankcs/HanLP/wiki/%E6%96%87%E6%9C%AC%E5%88%86%E7%B1%BB%E4%B8%8E%E6%83%85%E6%84%9F%E5%88%86%E6%9E%90");
            System.exit(1);
        }

        IClassifier classifier = new NaiveBayesClassifier(); // 创建分类器，更高级的功能请参考IClassifier的接口定义
        classifier.train(CORPUS_FOLDER);                     // 训练后的模型支持持久化，下次就不必训练了
        model = (NaiveBayesModel) classifier.getModel();
        IOUtil.saveObjectTo(model, MODEL_PATH);
        return model;
    }
}
