package com.example.yespaysgallery.repository;
import org.springframework.data.repository.CrudRepository;

import com.example.yespaysgallery.model.Category;

public interface CategoryRepository extends CrudRepository<Category, Integer>{
    public Category findByCategoryName(String name);
    
}
