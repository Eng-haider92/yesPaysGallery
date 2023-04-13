package com.example.yespaysgallery.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.example.yespaysgallery.model.Category;
import com.example.yespaysgallery.model.Image;

public interface ImageRepository extends CrudRepository<Image ,Long>{

    public List<Image> findAll();
    public List<Image> findByStatusIsNull();
    public List<Image> findByStatus(Boolean status);
    public List<Image> findAllByCategoryAndStatus(Category c, Boolean status);


    
}
