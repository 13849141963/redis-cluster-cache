package com.zy.cn.dao;
import com.zy.cn.entity.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.*;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:spring-data-redis.xml")
public class TestSpringDataRedisCluster {

    //使用spring-data操作redis集群
    @Autowired
    private RedisTemplate redisTemplate;

    //spring-date 操作String类型
    @Test
    public void testRedisTemplateList(){
        //使用redisTemplate操作String类型
        ValueOperations valueOperations = redisTemplate.opsForValue();
        //String类型  设置key:放入对象
        //valueOperations.set("user",new User(1,"马小跳",18,new Date()));
        //获取key:直接获取对象
        Object user1 = valueOperations.get("user");
        System.out.println(user1);
        //删除key
        redisTemplate.delete("user");


    }


    //spring-data操作list类型
    @Test
    public void testRedisTemplate(){
        //使用redisTemplate操作List类型
        ListOperations listOperations = redisTemplate.opsForList();
        //使用redisTemplate操作Set类型
        SetOperations setOperations = redisTemplate.opsForSet();
        //使用redisTemplate操作zset类型
        ZSetOperations zSetOperations = redisTemplate.opsForZSet();
        listOperations.leftPush("list", Arrays.asList(new User(1, "马小跳", 18, new Date()), new User(1, "马小跳", 18, new Date())));

        List list = listOperations.range("list", 0, -1);

        for (Object o : list) {
            System.out.println(o);
        }
    }

    //spring-data操作list类型
    @Test
    public void testRedisTemplateHash(){
        //使用redisTemplate操作Set类型
        SetOperations setOperations = redisTemplate.opsForSet();
        //使用redisTemplate操作zset类型
        ZSetOperations zSetOperations = redisTemplate.opsForZSet();
        //使用redisTemplate操作hash类型
        HashOperations hashOperations = redisTemplate.opsForHash();

        hashOperations.put("map","name","张三");

        Object o = hashOperations.get("map", "name");

        System.out.println(o);
    }
    @Test
    public void testHash(){
        //spring-data操作redis的hash类型
        HashOperations hash = redisTemplate.opsForHash();
        hash.put("users",
                "com.zy.cn.UserServiceImpl",
                Arrays.asList(new User(1,"张三",18,new Date()),new User(2,"李四",23,new Date())));

        List<User> users = (List<User>) hash.get("users", "com.zy.cn.UserServiceImpl");
        for (User user : users) {
            System.out.println(user);
        }

        //判断hash是否存在key
        Boolean users1 = hash.hasKey("users", "com.zy.cn.UserServiceImpl");
        System.out.println(users1);

        //获取散列中所有的key集合
        Set<String> users2 = hash.keys("users");
        for (String s : users2) {
            System.out.println(s);
        }
        //删除的keys集合
        Long users3 = hash.delete("users", "com.zy.cn.UserServiceImpl");
        System.out.println(users3);

        Boolean users4 = hash.hasKey("users", "com.zy.cn.UserServiceImpl");
        System.out.println(users4);
    }


}
