package com.example.demo;

import com.example.demo.pojo.User;
import com.example.demo.utils.RedisUtils;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;

@SpringBootTest
class Redis02SpringbootApplicationTests {

    //使用RedisTemplate来测试
    @Autowired
    RedisTemplate redisTemplate;

    @Autowired
    StringRedisTemplate stringRedisTemplate;

    @Autowired
    private RedisUtils redisUtils;


    /**
     * 工具类测试
     */
    @Test
    void test3(){
        redisUtils.setRedisTemplate(stringRedisTemplate);
        redisUtils.set("name","shen");
        System.out.println( redisUtils.get("name"));
    }


    /**
     * redisTemplate 简单测试
     */
    @Test
    void contextLoads() {
        //redis中使用的是lettuce，有一些方法需要介绍
        //redisTemplate.opsForValue();     操作String字符串
        // redisTemplate.opsForList();      操作list
        //redisTemplate.opsForSet();       操作set
        //redisTemplate.opsForHash();      操作hash
        // redisTemplate.opsForZSet();        操作zset
        // redisTemplate.opsForGeo();         操作geo
        //。。。。以此类推，

        //建立连接
        RedisConnection connection = redisTemplate.getConnectionFactory().getConnection();
        //清空数据库
        connection.flushAll();
        //设置字符串
        redisTemplate.opsForValue().set("name","shen");
        //获取字符串
        Object name = redisTemplate.opsForValue().get("name");
        //输出
        System.out.println("name:"+name);


    }

    /**
     * 自定义redisTemplate测试   redis序列化
     * @throws JsonProcessingException
     */
    @Test
    void test() throws JsonProcessingException {
        //真实开发中一般都需要json来存储对象
        User user = new User("springboot和redis整合", 1);
        //String jsonUser = new ObjectMapper().writeValueAsString(user);
        redisTemplate.opsForValue().set("user",user);
        System.out.println("user:"+redisTemplate.opsForValue().get("user"));

        //user:{"name":"springboot和redis整合","age":1}  json序列化
    }

    /**
     * stringRedisTemplate 测试
     * @throws JsonProcessingException
     */
    @Test
    void test2() throws JsonProcessingException {
        //真实开发中一般都需要json来存储对象
        User user = new User("springboot和redis整合", 1);
        String jsonUser = new ObjectMapper().writeValueAsString(user);
        stringRedisTemplate.opsForValue().set("user",jsonUser);
        System.out.println("user:"+redisTemplate.opsForValue().get("user"));

        //user:{"name":"springboot和redis整合","age":1}
    }



}
