package com.cayden;

import com.cayden.domain.JDSkuMapper;
import com.cayden.webmagic.pageprocessor.InitRedis;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class JDSpiderApplicationTests {


	@Autowired
	StringRedisTemplate stringRedisTemplate;
	@Autowired
	JDSkuMapper jdSkuMapper;

	@Autowired
	InitRedis initRedis;


	@Test
	public void sku() throws Exception {

		List<Long> allSKUId = jdSkuMapper.getAllSKUId();
		System.out.println(allSKUId);
	}

	@Test
	public void redis(){
		String set = "set_item.jd.com";
		stringRedisTemplate.delete(set);
	}


	@Test
	public void contextLoads() {
		String word = "欢迎/v";
		if(word.matches("[\\u4e00-\\u9fa5]+/(n|v|a)")) {
			System.out.println(word.substring(0,word.length() - 2));
		}
	}

}
