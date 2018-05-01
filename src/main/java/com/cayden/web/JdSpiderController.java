package com.cayden.web;

import com.cayden.webmagic.pageprocessor.InitRedis;
import com.cayden.webmagic.pageprocessor.JDCatProcessor;
import com.cayden.webmagic.pageprocessor.JDPageProcessor;
import com.cayden.webmagic.pipeline.JDSpiderPipeline;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.monitor.SpiderMonitor;
import us.codecraft.webmagic.scheduler.RedisScheduler;

import javax.management.JMException;

/**
 * Created by CaydenPrivate on 17/10/8.
 */
@RestController
public class JdSpiderController {

    @Autowired
    JDSpiderPipeline jdSpiderPipeline;

    @Autowired
    JDPageProcessor jdPageProcessor;

    @Autowired
    JDCatProcessor jdCatProcessor;

    @Autowired
    InitRedis initRedis;

    /*
    @Autowired
    ProxyIpMapper proxyIpMapper;
    */
    @GetMapping("/jd")
    public String index(String cat,String thread) {
        /*
        List<ProxyIp> proxyList = proxyIpMapper.findAllProxies();
        proxyList = proxyList.subList(0,10);
        List<Proxy> proxies = new ArrayList<>(proxyList.size());
        for(ProxyIp proxyIp : proxyList) {
            proxies.add(new Proxy(proxyIp.getIp(), proxyIp.getPort()));
        }
        */
        /*HttpClientDownloader httpClientDownloader = new HttpClientDownloader();
        //设置动态转发代理，使用定制的ProxyProvider
        httpClientDownloader.setProxyProvider(CrowProxyProvider.from(new Proxy("forward.xdaili.cn", 80)));*/

        if(StringUtils.isBlank(cat)){
            //参数为空
            return "参数错误";
        }
        //默认线程数量为10
        int threadNum;
        try {
            threadNum = Integer.parseInt(thread);
        }catch (Exception e){
            threadNum = 10;
        }
        String url = "https://list.jd.com/list.html?cat=%s&page=1";
        url = String.format(url, cat);

        //使用redis来管理爬取队列
        String domain = "item.jd.com";
        initRedis.initSetUrl(domain);
        RedisScheduler redisScheduler = new RedisScheduler("localhost");

        Spider jdSpider = Spider.create(jdPageProcessor)
                //.setDownloader(httpClientDownloader)
                .addUrl(url)
                .addPipeline(jdSpiderPipeline)
                .setScheduler(redisScheduler)
                .thread(threadNum);
        //添加爬虫监控
        /*try {
            SpiderMonitor.instance().register(jdSpider);
        } catch (JMException e) {
            e.printStackTrace();
        }*/
        jdSpider.start();
        return "爬虫开启";
    }


    /**
     * 获取商品分类数据
     * @return
     */
    @GetMapping("/jd/cat")
    public String cat() {

        //分类列表页面
        String url = "https://www.jd.com/allSort.aspx";

        Spider jdSpider = Spider.create(jdCatProcessor)
                .addUrl(url)
                .addRequest()
                .addPipeline(jdSpiderPipeline)
                .thread(1);
        try {
            SpiderMonitor.instance().register(jdSpider);
            jdSpider.start();
        } catch (JMException e) {
            e.printStackTrace();
        }
        return "爬取分类";
    }

}
