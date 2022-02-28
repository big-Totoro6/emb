package com.zhd.emb.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

/**
 * redis配置类
 * @author
 * */
@Configuration
public class RedisConfig {
    /**
     * 配置序列化
     * 要放置连接工厂
     * */
    @Bean
    public RedisTemplate<String,Object> redisTemplate(RedisConnectionFactory redisConnectionFactory){
        RedisTemplate<String,Object> redisTemplate=new RedisTemplate<>();
        //String类型 key序列化
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        //String类型 value序列化
        redisTemplate.setValueSerializer(new GenericJackson2JsonRedisSerializer());
        //Hash类型 key序列化
        redisTemplate.setHashKeySerializer(new StringRedisSerializer());
        //Hash类型 value序列化
        redisTemplate.setHashValueSerializer(new GenericJackson2JsonRedisSerializer());
        //要放入连接工厂
        redisTemplate.setConnectionFactory(redisConnectionFactory);
        return redisTemplate;
    }
}
