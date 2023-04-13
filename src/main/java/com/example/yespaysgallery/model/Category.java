package com.example.yespaysgallery.model;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "category")
public class Category {
    private int id;
    private String categoryName;
    private Set<Image> images;


    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "category_id")
    public int getId(){
        return id;
    }

    public void setId(int id){
        this.id = id;
    }

    @Column(name = "category_name")
    public String getCategoryName(){
        return categoryName;
    }

    public void setCategoryName(String cName){
        this.categoryName = cName;
    }

    @OneToMany(mappedBy = "category")
    public Set<Image> getImages(){
         return images;
    }
    
    public void setImages(Set<Image> images){
        this.images = images;
    }
    
}
