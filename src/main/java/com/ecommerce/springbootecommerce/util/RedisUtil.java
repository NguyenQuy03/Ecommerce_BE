package com.ecommerce.springbootecommerce.util;

import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import com.ecommerce.springbootecommerce.constant.SystemConstant;

@Component
public class RedisUtil {
    @Autowired
    private RedisTemplate<String, Object> redisTemplate;
    
//    public void setHashField(String key, String field, Object value) {
//        HashOperations<String, String, Object> hashOps = redisTemplate.opsForHash();
//        hashOps.put(key, field, value);
//    }
    
//    public void setHashFieldWithExpiration(String key, String field, Object value, long expirationTimeInSeconds) {
//        HashOperations<String, String, Object> hashOps = redisTemplate.opsForHash();
//        hashOps.put(key, field, value);
//        redisTemplate.expire(key, expirationTimeInSeconds, TimeUnit.SECONDS);
//    }
    public Object getHashField(String key, String field) {
        HashOperations<String, String, Object> hashOps = redisTemplate.opsForHash();
        return hashOps.get(key, field);
    }

    public Object getKey(String key) {
        return redisTemplate.opsForValue().get(key);
    }
    
    public void setKey(String key, String value, long expireTime) {
        redisTemplate.opsForValue().set(key, value, expireTime, TimeUnit.SECONDS);
    }

    public void removeKey(String key) {
        if(key.equals(null)) {
            return;
        }
        redisTemplate.delete(key);
    }

    public boolean isTokenValid(String key) {
        Long ttl = redisTemplate.getExpire(key);
        return ttl != null && ttl <= 0;
    }

}
