package com.example.demo.Service.Tools;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import tools.jackson.core.type.TypeReference;
import tools.jackson.databind.ObjectMapper;

import java.util.concurrent.TimeUnit;

@Service
public class RedisService {
    private final ObjectMapper mapper = new ObjectMapper();

    @Autowired
    private RedisTemplate<String,Object> redisTemplate;

    public <T> T get(String key, TypeReference<T> typeReference){
        try {
            Object returnValue = redisTemplate.opsForValue().get(key);
            if(returnValue == null) return null;

            return mapper.readValue(returnValue.toString(), typeReference);
        } catch (Exception e) {
            System.out.println("There is an error at get in redisTemplate "+e.getMessage());
            return null;
        }
    }

    public void set(String key, Object toSave, Long ttl){
        try{
            String jsonValue = mapper.writeValueAsString(toSave);
            redisTemplate.opsForValue().set(key,jsonValue,ttl,TimeUnit.MINUTES);
        } catch (Exception e) {
            System.out.println("There is an error at set in redisTemplate "+e.getMessage());
            return;
        }
    }
}
