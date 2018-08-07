package com.zy.cn.redis.lock1;

import com.zy.cn.util.SnowflakeIdWorker;
import org.junit.Test;
import redis.clients.jedis.Jedis;

public class TestLockRedisClient2 {

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
        boolean b = RedisLockUtil.tryGetDistributedLock(jedis, "187", String.valueOf(12345l), 60000);
        System.out.println("是否获取锁:"+b);

        //Jedis jedis, String lockKey, String requestId
        boolean b1 = RedisLockUtil.releaseDistributedLock(jedis, "187", String.valueOf(12345l));

        System.out.println("是否释放锁:"+b1);


    }
}
