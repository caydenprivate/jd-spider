package com.cayden.webmagic.pageprocessor;

import com.cayden.domain.JDCat;
import com.cayden.utils.UserAgentUtil;
import org.springframework.stereotype.Component;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.processor.PageProcessor;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by CaydenPrivate on 17/10/11.
 */
@Component
public class JDCatProcessor implements PageProcessor {//修改改类，定制自己的抽取逻辑


    //抓取网站的相关配置，包括编码、抓取间隔、重试次数、代理、UserAgent等
    private Site site = Site.me()
            .setDisableCookieManagement(true)
            //.setCharset("UTF-8")
            .setTimeOut(30000)
            .setRetryTimes(3)
            .setDomain("item.jd.com")
            .setCycleRetryTimes(3)//循环重试次数
            .setSleepTime(new Random().nextInt(20)*100)
            .setUserAgent(UserAgentUtil.getRandomUserAgent());

    @Override
    public void process(Page page) {

        List<JDCat> jdCatList = new ArrayList<>();

        //一级分类
        String catName = "//*[@class='item-title']/span/text()";
        List<String> catNameList = page.getHtml().xpath(catName).all();
        for (int i = 0; i < catNameList.size(); i++) {
            String catNameStr =  catNameList.get(i);
            JDCat jdCat = new JDCat();
            jdCat.setCatName(catNameStr);
            jdCat.setCatStage(1);
            jdCat.setCreateAt(System.currentTimeMillis()/1000);
            jdCatList.add(jdCat);
        }

        //二级分类
        String catName2 = "//*[@class='clearfix']/dt/a/text()";
        String catUrl2 = "//*[@class='clearfix']/dt/a/@href";
        List<String> catNameList2 = page.getHtml().xpath(catName2).all();
        List<String> catUrlList2 = page.getHtml().xpath(catUrl2).all();
        for (int i = 0; i < catUrlList2.size(); i++) {
            String catNameStr = catNameList2.get(i);
            String catUrlStr =  catUrlList2.get(i);
            JDCat jdCat = new JDCat();
            jdCat.setCatName(catNameStr);
            jdCat.setCatUrl(catUrlStr);
            jdCat.setCatStage(2);
            jdCat.setCreateAt(System.currentTimeMillis()/1000);
            jdCatList.add(jdCat);
        }

        //三级分类
        String catName3 = "//*[@class='clearfix']/dd/a/text()";
        String catUrl3 = "//*[@class='clearfix']/dd/a/@href";
        List<String> catNameList3 = page.getHtml().xpath(catName3).all();
        List<String> catUrlList3 = page.getHtml().xpath(catUrl3).all();
        for (int i = 0; i < catUrlList3.size(); i++) {
            String catNameStr = catNameList3.get(i);
            String catUrlStr =  catUrlList3.get(i);

            JDCat jdCat = new JDCat();
            String regex = "(\\d+,\\d+,\\d+)";
            Pattern pattern = Pattern.compile(regex);
            Matcher matcher = pattern.matcher(catUrlStr);
            if(matcher.find()){
                String catId = matcher.group(1);
                jdCat.setCatId(catId);
            }

            jdCat.setCatName(catNameStr);
            jdCat.setCatUrl(catUrlStr);
            jdCat.setCatStage(3);
            jdCat.setCreateAt(System.currentTimeMillis()/1000);
            jdCatList.add(jdCat);
        }

        page.putField("jdCatInfo",jdCatList);

    }

    @Override
    public Site getSite() {
        return site;
    }

}
