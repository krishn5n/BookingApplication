package com.example.demo.Config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJacksonJsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import tools.jackson.databind.ObjectMapper;

@Configuration
public class RedisConfig {

    @Bean
    public RedisTemplate<String,Object> redisTemplate(RedisConnectionFactory redisConnectionFactory){
        RedisTemplate<String,Object> returnTemplate = new RedisTemplate<>();
        returnTemplate.setConnectionFactory(redisConnectionFactory);
        returnTemplate.setKeySerializer(new StringRedisSerializer());
        returnTemplate.setValueSerializer(new GenericJacksonJsonRedisSerializer(new ObjectMapper()));

        return returnTemplate;
    }
}
