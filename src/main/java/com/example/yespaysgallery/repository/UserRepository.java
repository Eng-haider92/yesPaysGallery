package com.example.yespaysgallery.repository;

import org.springframework.data.repository.CrudRepository;

import com.example.yespaysgallery.model.User;


public interface UserRepository extends CrudRepository<User, Long> {
    User findByEmail(String email);
	User findByUsername(String name);
    
}
