package com.example.demo;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;

@SpringBootTest
class DemoApplicationTests {

	@Autowired
	private RedisTemplate redisTemplate;


	@Disabled
	@Test
	void contextLoads(){
		redisTemplate.opsForValue().set("name","krishnan_2");
//		Object value = redisTemplate.opsForValue().set("name","krishnan_2");
	}

}
