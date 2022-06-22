package com.agosh.userinfo.service;


import com.agosh.userinfo.model.User;

public interface IUserService {
    User getUserInfo(String email);

    User addUser(User user);

    User updateUser(User user);

    User deleteUser(String email);
}
