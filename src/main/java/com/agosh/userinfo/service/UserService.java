package com.agosh.userinfo.service;

import com.agosh.userinfo.model.User;
import com.agosh.userinfo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService implements IUserService{

    @Autowired
    UserRepository userRepository;

    @Override
    public User getUserInfo(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public User addUser(User user) {
        return userRepository.save(user);
    }

    @Override
    public User updateUser(User user) {
        User latestUser = this.getUserInfo(user.getEmail());
        latestUser.setDateOfBirth(user.getDateOfBirth());
        latestUser.setLastName(user.getLastName());
        latestUser.setFirstName(user.getFirstName());
        return userRepository.save(latestUser);
    }

    @Override
    public User deleteUser(String email) {
        User user =  this.getUserInfo(email);
        userRepository.deleteById(user.getId());
        return user;

    }

}
