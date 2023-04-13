package com.example.yespaysgallery.services;

import com.example.yespaysgallery.model.User;

public interface UserService {
    public User savUser(User user);
    public User findUserByUsername(String username);
    public void saveUser(User user);
    
}
