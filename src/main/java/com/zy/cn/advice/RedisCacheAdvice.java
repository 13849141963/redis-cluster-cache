package com.zy.cn.advice;

import com.alibaba.fastjson.JSONObject;
import com.zy.cn.annonations.Cache;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.DigestUtils;
import redis.clients.jedis.JedisCluster;

import java.lang.reflect.Method;

/****
 * 通用缓存类:对于不经常变动的数据作缓存
 */
@Component("redisCacheAdvice")
public class RedisCacheAdvice implements MethodInterceptor {


    @Autowired
    private JedisCluster jedisCluster;

    /*****
     * 1.判断redis是否存在key
     * 2.如果存在直接返回
     * 3.如果不存在则执行目标方法放入redis中
     * @param invocation
     * @return
     * @throws Throwable
     */
    public Object invoke(MethodInvocation invocation) throws Throwable {
       /* System.out.println("方法的名字"+invocation.getMethod().getName());
        System.out.println("方法的参数");
        Object[] arguments = invocation.getArguments();
        for (Object argument : arguments) {
            System.out.println(argument);
        }*/
        //获取缓存的key
        String key = generateKey(invocation);
        //获取目标类的全限定名称[为了在redis中更模块化]
        String mapname = invocation.getThis().getClass().getName();
        Object proceed = null;
        //获取方法对象
        Method method = invocation.getMethod();
        //判断当前执行的目标方法是否存在该类型注解
        if (invocation.getMethod().isAnnotationPresent(Cache.class)) {


            //第一种方案:使用JedisCluster对象操作集群
            //判断redis是否存在key
            if (jedisCluster.hexists(mapname, key)) {
                //存在key
                String jsonString = jedisCluster.hget(mapname, key);
                //将json转化成对应的返回值类型
                proceed = JSONObject.parseObject(jsonString, method.getGenericReturnType());
            } else {
                //不存在key放行目标方法
                proceed = invocation.proceed();
                //将结果转成json
                String jsonString = JSONObject.toJSONString(proceed);
                //把结果放入redis中
                //jedisCluster.set(key,jsonString);
                jedisCluster.hset(mapname, key, jsonString);
            }
            //第二种方案
            //使用spring-data的RedisTemplate对象操作redis集群
        } else {
            //不存在注解放行目标方法
            proceed = invocation.proceed();
        }
        return proceed;
    }


    //设置缓存的key:(方法的全限定名称+方法的参数)MD5
    private String generateKey(MethodInvocation invocation) {
        StringBuilder builder = new StringBuilder();
        //获取方法的名字
        String methodName = invocation.getMethod().getName();
        //追加到StringBuilder中
        builder.append(methodName);
        //获取方法的参数
        Object[] arguments = invocation.getArguments();
        for (Object argument : arguments) {
            //目标方法的参数到StringBuilder中
            builder.append(argument);
        }
        //对方法的全限定名称以及方法的参数做MD5加密
        return DigestUtils.md5DigestAsHex(builder.toString().getBytes());
    }
}
