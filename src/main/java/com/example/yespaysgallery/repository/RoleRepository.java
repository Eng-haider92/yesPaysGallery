package com.example.yespaysgallery.repository;

import org.springframework.data.repository.CrudRepository;

import com.example.yespaysgallery.model.Role;



public interface RoleRepository  extends CrudRepository<Role , Integer>{

	Role findByRole(String string);
	void deleteByRole(String string);

}