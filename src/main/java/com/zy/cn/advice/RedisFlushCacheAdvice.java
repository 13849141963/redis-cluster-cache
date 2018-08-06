package com.zy.cn.advice;

import com.zy.cn.annonations.ClearCache;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import redis.clients.jedis.JedisCluster;

/****
 * 清除redis的缓存类
 */
@Component("redisFlushCacheAdvice")
public class RedisFlushCacheAdvice implements MethodInterceptor {

    @Autowired
    private JedisCluster jedisCluster;

    public Object invoke(MethodInvocation invocation) throws Throwable {
        Object proceed = null;
        //获取类的全想定名称
        String mapname = invocation.getThis().getClass().getName();
        try {
            //判断当前方法是否存在清除缓存类型的注解
            if (invocation.getMethod().isAnnotationPresent(ClearCache.class)) {
                //放行执行目标方法
                proceed = invocation.proceed();
                //清楚map模块中的缓存
                jedisCluster.del(mapname);
            } else {
                //执行目标方法
                proceed = invocation.proceed();
            }
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        return proceed;

    }
}
