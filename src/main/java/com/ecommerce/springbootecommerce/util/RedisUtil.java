package com.ecommerce.springbootecommerce.util;

import java.util.concurrent.TimeUnit;

import com.ecommerce.springbootecommerce.constant.RedisConstant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import com.ecommerce.springbootecommerce.constant.SystemConstant;

@Component
public class RedisUtil {
    @Autowired
    private RedisTemplate<String, String> redisTemplate;
    
    public void setHashField(String key, String field, String value) {
        HashOperations<String, String, String> hashOps = redisTemplate.opsForHash();
        hashOps.put(key, field, value);
    }
    
//    public void setHashFieldWithExpiration(String key, String field, Object value, long expirationTimeInSeconds) {
//        HashOperations<String, String, Object> hashOps = redisTemplate.opsForHash();
//        hashOps.put(key, field, value);
//        redisTemplate.expire(key, expirationTimeInSeconds, TimeUnit.SECONDS);
//    }
    public Object getHashField(String key, String field) {
        HashOperations<String, String, String> hashOps = redisTemplate.opsForHash();
        return hashOps.get(key, field);
    }

    public Object getKey(String key) {
        return redisTemplate.opsForValue().get(key);
    }
    
    public void setKey(String key, String value, long expireTime) {
        redisTemplate.opsForValue().set(key, value, expireTime, TimeUnit.SECONDS);
    }

    public void removeKey(String key) {
        redisTemplate.delete(key);
    }

    public void setQuantityOrder(long value) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        long quantityOrder = Long.parseLong((String) getHashField(RedisConstant.REDIS_USER_INFO + username, RedisConstant.REDIS_USER_INFO_QUANTITY_ORDER));
        setHashField(RedisConstant.REDIS_USER_INFO + username, RedisConstant.REDIS_USER_INFO_QUANTITY_ORDER, String.valueOf(quantityOrder + value));
    }

}
