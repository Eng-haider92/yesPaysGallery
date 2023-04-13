package com.example.yespaysgallery.model;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;


@Entity
@Table(name = "image")
public class Image{

    private Long id;
    private String imagePath;
    private String description;
    private Boolean status;
    private Category category;
    private User user;

    public Image(){

    }
    public Image(Long id, String imgPath, String desc, Boolean status){
        this.id = id;
        this.imagePath = imgPath;
        this.description = desc;
        this.status = status;

    }


    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "image_id")
    public Long getId(){
        return id;
    }

    public void setId(Long id ){
        this.id = id;
    }

    @Column(name = "image_Path")
    public String getImagePath(){
        return imagePath;
    }

    public void setImagePath(String imgPath){
        this.imagePath = imgPath;
    }

    @Column(name = "description")
    public String getDescription(){
        return description;
    }

    public void setDescription(String desc){
        this.description = desc;
    }

    @Column(name = "status")
    public Boolean getStatus(){
        return status;
    }

    public void setStatus(Boolean stat){
        this.status = stat;
    }

    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "category_id")
    public Category getCategory(){
        return category;
    }

    public void setCategory(Category c){
        this.category = c;
    }


    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "user_id")
    public User getUser(){
        return user;
    }

    public void setUser(User u){
        this.user = u;
    }
    

    @Override
	public String toString() {
		return "Image [id=" + id + ", image Path=" + imagePath + ", description=" + description + ", category=" + category + ", user=" + user + "]";
	}
    
}
