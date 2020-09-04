package com.example.demo.config;


import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@Configuration
public class RedisConfig {
    //自定义redisTemplate

    @Bean
    public RedisTemplate<String,Object> redisTemplate(RedisConnectionFactory connectionFactory){
        RedisTemplate<String,Object> redisTemplate=new RedisTemplate<>();
        redisTemplate.setConnectionFactory(connectionFactory);
        //配置不同的序列化
        Jackson2JsonRedisSerializer<Object> objectJackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer<Object>(Object.class);

        ObjectMapper objectMapper = new ObjectMapper();

        objectMapper.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);

        objectMapper.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);


        //String的序列化
        StringRedisSerializer stringRedisSerializer=new StringRedisSerializer();

        //key 采用的String的序列化方式
        redisTemplate.setKeySerializer(stringRedisSerializer);
        //hash 的key也采用的是String的序列化方式
        redisTemplate.setHashKeySerializer(stringRedisSerializer);
        //value 序列化方式采用的jackson序列化
        redisTemplate.setValueSerializer(objectJackson2JsonRedisSerializer);
        //hash value 序列化方式采用的jackson
        redisTemplate.setHashValueSerializer(objectJackson2JsonRedisSerializer);
        redisTemplate.afterPropertiesSet();
        return redisTemplate;
    }
}
