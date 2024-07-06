package com.libraryservice.component;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

// @Component
public class RedisCacheClearer implements ApplicationListener<ContextRefreshedEvent> {

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        // Clear all keys from Redis
        redisTemplate.getConnectionFactory().getConnection().flushDb();
        System.out.println("Redis cache cleared on application restart");
    }
}

