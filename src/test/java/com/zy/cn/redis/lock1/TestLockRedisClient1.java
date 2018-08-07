package com.zy.cn.redis.lock1;

import com.zy.cn.util.SnowflakeIdWorker;
import org.junit.Test;
import redis.clients.jedis.Jedis;

import java.text.SimpleDateFormat;
import java.util.Date;

public class TestLockRedisClient1 {

    //
    @Test
    public void test(){

        Jedis jedis = new Jedis("192.168.87.129",6379);
        /** @param jedis Redis客户端
         * @param lockKey 锁
         * @param requestId 请求标识
         * @param expireTime 超期时间
         * */
        long snowflak = SnowflakeIdWorker.getSnowflak();
        System.out.println("当前时间戳"+snowflak);
        boolean b = RedisLockUtil.tryGetDistributedLock(jedis, "187", String.valueOf(12345l), 60000);
        System.out.println("是否获取锁:"+b);

        //Jedis jedis, String lockKey, String requestId
        boolean b1 = RedisLockUtil.releaseDistributedLock(jedis, "187", String.valueOf(12345l));

        System.out.println("是否释放锁:"+b1);

        System.out.println("=========");

    }

    public static void main(String[] args) {
            //分配 Date 对象并初始化此对象，以表示自从标准基准时间（称为“历元（epoch）”，
            //即 1970 年 1 月 1 日 00:00:00 GMT）以来的指定毫秒数
            Date date =new Date(3600);
            //使用日期格式化类完成日期到格式化字符串的输出
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss:SS");
            System.out.println(format.format(date));
    }


}
