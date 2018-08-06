package com.zy.cn.service;

import com.zy.cn.annonations.Cache;
import com.zy.cn.annonations.ClearCache;
import com.zy.cn.dao.UserDAO;
import com.zy.cn.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service("userService")
@Transactional
public class UserServiceImpl implements UserService {
    @Autowired
    private UserDAO userDAO;

    @ClearCache
    public void insert(User user) {
        userDAO.insert(user);
    }

    @ClearCache
    public void delete(Integer id) {
        userDAO.delete(id);
    }

    @Cache
    public User find(Integer id) {
        return userDAO.find(id);
    }

    @Cache
    public List<User> queryAll() {
        return userDAO.queryAll();
    }
}
