package com.zy.cn.redis.lock2;

import org.junit.Test;

public class TestLockRedisClient2 {

    //
    @Test
    public void test(){

        boolean lock = DistributedLockUtil.lock("5331433");



    }
}
