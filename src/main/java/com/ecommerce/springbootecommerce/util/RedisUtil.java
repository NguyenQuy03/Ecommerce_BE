package com.ecommerce.springbootecommerce.util;

import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import com.ecommerce.springbootecommerce.constant.RedisConstant;

@Component
public class RedisUtil {
    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    public void setHashField(String key, String field, String value) {
        HashOperations<String, String, String> hashOps = redisTemplate.opsForHash();
        hashOps.put(key, field, value);
    }

    public String getHashField(String key, String field) {
        HashOperations<String, String, String> hashOps = redisTemplate.opsForHash();
        return hashOps.get(key, field);
    }

    public String getKey(String key) {
        return redisTemplate.opsForValue().get(key);
    }

    public void setKey(String key, String value, long expireTime) {
        redisTemplate.opsForValue().set(key, value, expireTime, TimeUnit.SECONDS);
    }

    public void removeKey(String key) {
        if (key != null && redisTemplate.hasKey(key)) {
            redisTemplate.delete(key);
        }
    }

    public void setQuantityOrder(long value) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        long quantityOrder = Long.parseLong((String) getHashField(RedisConstant.REDIS_USER_INFO + username,
                RedisConstant.REDIS_USER_INFO_QUANTITY_ORDER));
        setHashField(RedisConstant.REDIS_USER_INFO + username, RedisConstant.REDIS_USER_INFO_QUANTITY_ORDER,
                String.valueOf(quantityOrder + value));
    }

    public void adjustQuantityOrder(String username, int quantity) {
        HashOperations<String, String, String> hashOps = redisTemplate.opsForHash();
        String key = RedisConstant.REDIS_USER_INFO + username;
        String field = RedisConstant.REDIS_USER_INFO_QUANTITY_ORDER;
        hashOps.increment(key, field, quantity);
    }
}
