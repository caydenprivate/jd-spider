package com.cayden.webmagic.pageprocessor;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.cayden.domain.*;
import com.cayden.utils.URLGeneratedUtil;
import com.cayden.utils.UserAgentUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.processor.PageProcessor;

import java.util.Collections;
import java.util.List;
import java.util.Random;

/**
 * Created by CaydenPrivate on 17/10/11.
 */
@Component
public class JDPageProcessor implements PageProcessor {//修改改类，定制自己的抽取逻辑

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    public static final String URL_ITEM = "https://item\\.jd\\.com/\\d+\\.html";
    public static final String URL_LIST = "https:\\/\\/list\\.jd\\.com\\/list\\.html\\?cat=\\d+,\\d+,\\d+&page=\\d+.*";
    public static final String URL_FIRST_PAGE = "https:\\/\\/list\\.jd\\.com\\/list\\.html\\?cat=\\d+,\\d+,\\d+&page=[0,1]&.*";


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
        //商品列表页，将sku信息加入爬取列表，将分页加入爬取列表
        if (page.getUrl().regex(URL_LIST).match()) {
            //将分页链接加入爬取列表
            List<String> listUrls = page.getHtml().links().regex(URL_LIST).all();
            List<String> listFirst = page.getHtml().links().regex(URL_FIRST_PAGE).all();
            listUrls.removeAll(listFirst);
            page.addTargetRequests(listUrls);

            //获取页面商品信息
            String base = "//li[@class='gl-item']";
            String sku = base + "/div/@data-sku";
            List<String> skuList = page.getHtml().xpath(sku).all();
            //将item加入爬取列表，排除已经保存信息的sku

            for (int i = 0; i < skuList.size(); i++) {
                String url = URLGeneratedUtil.getJDItemUrl(skuList.get(i));
                page.addTargetRequest(url);
            }


        }

        //详情页取信息，并将其他规格的信息加入爬取列表
        if (page.getUrl().regex(URL_ITEM).match()) {

            String title = "//*[@class='itemInfo-wrap']/div[@class='sku-name']/text()";
            String img = "//*[@id='spec-img']/@data-origin";
            String shopName = "//*[@class='J-hove-wrap EDropdown fr']/div[1]/div/a/@title";
            String productName = "//*[@class='item ellipsis']/@title";
            String parameter = "//*[@class='p-parameter']/ul/li/@title";
            String bandName = "//*[@id='parameter-brand']/li/@title";

            String titleStr = page.getHtml().xpath(title).toString();
            String imgStr = page.getHtml().xpath(img).toString();
            String shopNameStr = page.getHtml().xpath(shopName).toString();
            String productNameStr = page.getHtml().xpath(productName).toString();
            String brandNameStr = page.getHtml().xpath(bandName).toString();

            List<String> parameters = page.getHtml().xpath(parameter).all();
            //去除空白元素
            parameters.removeAll(Collections.singleton(""));
            String parameterStr = StringUtils.join(parameters, ",");

            //通过正则从url中取商品skuId
            String skuStr = page.getUrl().regex("(\\d+)").toString();
            String catRegex = "cat: \\[(.+?)\\],";
            String venderIdRegex = "venderId:(.+?),";
            String shopIdRegex = "shopId:'(.+?)',";
            String brandIdRegex = "brand: (.+?),";
            String catId = page.getHtml().regex(catRegex).toString();
            String venderId = page.getHtml().regex(venderIdRegex).toString();
            String shopId = page.getHtml().regex(shopIdRegex).toString();
            String brandId = page.getHtml().regex(brandIdRegex).toString();

            String jdHK = page.getHtml().regex("全球购").toString();
            if(StringUtils.isNotBlank(jdHK)){

                String shop = "//*[@class='mt']/h3/a/@title";
                String image = "//*[@id='spec-n1']/img/@src";
                shopNameStr =  page.getHtml().xpath(shop).toString();
                imgStr =  page.getHtml().xpath(image).toString();
                productNameStr = titleStr;
                brandNameStr = "全球购";
            }

            JDSku jdSku = new JDSku();
            jdSku.setCatId(catId);
            jdSku.setImgUrl(imgStr);
            jdSku.setProductTitle(titleStr);
            jdSku.setShopName(shopNameStr);
            jdSku.setProductName(productNameStr);
            jdSku.setSkuId(Long.parseLong(skuStr));
            jdSku.setProductDesc(parameterStr);
            jdSku.setBrandName(brandNameStr);
            jdSku.setCreatedAt(System.currentTimeMillis() / 1000);

            if(StringUtils.isNotBlank(brandId)){
                jdSku.setBrandId(Long.parseLong(brandId));
            }
            if(StringUtils.isNotBlank(venderId)){
                jdSku.setVenderId(Long.parseLong(venderId));
            }
            if(StringUtils.isNotBlank(shopId)){
                jdSku.setJdzyShopId(Long.parseLong(shopId));
            }

            page.putField("jdSkuInfo", jdSku);
            //将同一个商品中其他规格加入爬取列表
            //colorSize: [{"skuId":27532379652,"版本":"6G 64G"},{"skuId":27532379653,"版本":"8G 128G"}],
            String regex = "colorSize: (.+?), ";
            String colorSize = page.getHtml().regex(regex).toString();
            if (StringUtils.isNotBlank(colorSize) && colorSize.startsWith("[")) {
                //有规格信息
                JSONArray skuList = JSONArray.parseArray(colorSize);
                for (int i = 0; i < skuList.size(); i++) {
                    JSONObject json = (JSONObject) skuList.get(i);
                    long sku2 = json.getLong("skuId");
                    String url = URLGeneratedUtil.getJDItemUrl(sku2);
                    page.addTargetRequest(url);
                }
            }
        }
    }


    @Override
    public Site getSite() {
        return site;
    }

}
