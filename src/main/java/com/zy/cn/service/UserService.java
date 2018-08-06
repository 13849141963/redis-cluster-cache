package com.zy.cn.service;

import com.zy.cn.entity.User;

import java.util.List;

public interface UserService {
    public void insert(User user);

    public void delete(Integer id);

    public User find(Integer id);

    public List<User> queryAll();
}
