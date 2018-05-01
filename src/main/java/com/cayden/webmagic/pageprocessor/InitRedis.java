package com.cayden.webmagic.pageprocessor;

import com.cayden.domain.JDSkuMapper;
import com.cayden.utils.URLGeneratedUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class InitRedis {

    @Autowired
    StringRedisTemplate stringRedisTemplate;

    @Autowired
    JDSkuMapper jdSkuMapper;

    /**
     * 从数据库同步已经爬取过的页面到redis中
     * @param domain
     */
    public void initSetUrl(String domain){
        String set = "set_" + domain;
        String queue = "queue_" + domain;

        //清空已爬取redis
        stringRedisTemplate.delete(set);
        stringRedisTemplate.delete(queue);
        //从数据库查询已爬取过的url，加入redis set_xxx中
        List<Long> skuIds = jdSkuMapper.getAllSKUId();
        for (int i = 0; i < skuIds.size(); i++) {
            Long sku =  skuIds.get(i);
            stringRedisTemplate.opsForSet().add(set,URLGeneratedUtil.getJDItemUrl(sku));
        }

    }

}
