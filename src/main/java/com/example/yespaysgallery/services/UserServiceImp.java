package com.example.yespaysgallery.services;

import java.util.Arrays;
import java.util.HashSet;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.yespaysgallery.model.Role;
import com.example.yespaysgallery.model.User;
import com.example.yespaysgallery.repository.RoleRepository;
import com.example.yespaysgallery.repository.UserRepository;

@Service("userService")
public class UserServiceImp implements UserService{


    @Autowired
    private UserRepository userRepository;


    @Autowired
    private RoleRepository roleRepository;
    
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    
    @Override
    public User findUserByUsername(String username){
        User user = userRepository.findByUsername(username);
        return user;
    }

    
    @Override
    public void saveUser(User user){
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        user.setActive(1);
        Role userRole = roleRepository.findByRole("USER");
        user.setRoles(new HashSet<Role>(Arrays.asList(userRole)));
        userRepository.save(user);        
    }


    @Override
    public User savUser(User user) {
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        user.setActive(1);
        Role userRole = roleRepository.findByRole("USER");
        user.setRoles(new HashSet<Role>(Arrays.asList(userRole)));
        userRepository.save(user);        
        return user;
    }

    
}
