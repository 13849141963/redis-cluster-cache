package com.zy.cn.dao;

import com.zy.cn.entity.User;
import com.zy.cn.service.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Date;
import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:applicationContext.xml")
public class TestMybeis {
    @Autowired
    private UserService userService;

    @Test
    public void testInsert() {
        userService.insert(new User(null, "特斯啦", 18, new Date()));
    }

    @Test
    public void testDelete() {
        userService.delete(3);
    }

    @Test
    public void testFind() {
        User user = userService.find(4);
        System.out.println(user.getName());
        System.out.println("=====================");
        User user2 = userService.find(2);
        User user3 = userService.find(2);
    }

    @Test
    public void testquery() {
        List<User> users = userService.queryAll();
        for (User user : users) {
            System.out.println(user);
        }
        System.out.println("=====================");
        List<User> users1 = userService.queryAll();
        List<User> users2 = userService.queryAll();
        List<User> users3 = userService.queryAll();
        List<User> users4 = userService.queryAll();
    }
}
