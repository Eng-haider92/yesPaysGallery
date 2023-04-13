package com.example.yespaysgallery.services;

import com.example.yespaysgallery.model.Category;
import com.example.yespaysgallery.model.Image;
import com.example.yespaysgallery.model.User;

import java.io.IOException;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

public interface ImageService {
    public Image saveImage(Image img);
    public Image findById(Long id);
    public List<Image> findAllImages();
    public void saveImage( Image image, 
                            MultipartFile file,
                            String category, 
                            User user) throws IOException;
    
    public List<Image> findByStatusIsNull();
    public List<Image> findByStatus(Boolean status);
    public List<Image> findAllByCategoryAndStatus(Category c, Boolean status);
    public void updateImageStatus(Image i, Boolean status);


    
}
