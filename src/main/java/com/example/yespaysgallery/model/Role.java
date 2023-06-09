package com.example.yespaysgallery.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "role")
public class Role {

    private int id;
    private String role;


    
	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name="role_id")
    public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	@Column(name="role")
	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}
    
}
