package com.zy.cn.dao;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.BoundHashOperations;
import org.springframework.data.redis.core.BoundListOperations;
import org.springframework.data.redis.core.BoundValueOperations;
import org.springframework.data.redis.core.RedisTemplate;
import java.util.List;
import java.util.Set;
public class TestSpringDataBoundKey extends TestSpringDataRedisCluster{

    //使用spring-data操作redis集群
    //对于常用的key进行绑定.


    @Autowired
    private RedisTemplate redisTemplate;

    //spring-data 绑定String
    @Test
    public void testString(){
        //参数绑定要操作的key
        BoundValueOperations boundValueOperations = redisTemplate.boundValueOps("name");

        boundValueOperations.set("张三");

        boundValueOperations.set("李四");

        System.out.println(boundValueOperations.get());

        //修改键的名称
        boundValueOperations.rename("ss");
    }

    //spring-data 绑定list
    @Test
    public void testList() {
        BoundListOperations list = redisTemplate.boundListOps("list");
        list.leftPush("小黑");
        list.leftPush("小黑");
        list.leftPush("小黑");
        List range = list.range(0, -1);
        for (Object o : range) {
            System.out.println(o);
        }
    }


    //spring-data 绑定hash
    @Test
    public void testHash() {
        BoundHashOperations map = redisTemplate.boundHashOps("map");

        map.put("name","张三");
        map.put("age","18");
        //获取所有key
        Set keys = map.keys();
        for (Object key : keys) {
            System.out.println(key);
        }
        System.out.println("==============");
        //获取所有值
        List values = map.values();
        for (Object value : values) {
            System.out.println(value);
        }
    }
}
