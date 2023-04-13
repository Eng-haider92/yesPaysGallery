package com.example.yespaysgallery.services;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;

import com.example.yespaysgallery.model.Category;
import com.example.yespaysgallery.model.Image;
import com.example.yespaysgallery.model.User;
import com.example.yespaysgallery.repository.CategoryRepository;
import com.example.yespaysgallery.repository.ImageRepository;


@Service("galleryService")
public class ImageServiceImp implements ImageService{

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private ImageRepository imageRepository;

    @Override
    public List<Image> findAllImages() {
        List<Image> images = imageRepository.findAll();
        
        return images;
    }

    @Override
    public void saveImage(Image image, MultipartFile file, String categoryName, User user) throws IOException {
        String fileName = StringUtils.cleanPath(Objects.requireNonNull(file.getOriginalFilename()));

        image.setImagePath(fileName);
        image.setCategory(categoryRepository.findByCategoryName(categoryName));
        image.setUser(user);
        imageRepository.save(image);

        String uploadDirectory = "src/main/resources/static/images/gallerys/" + image.getId();
        saveFile(uploadDirectory, fileName, file);
    }


    public void saveFile(String uploadDirectory,String fileName,MultipartFile file) throws IOException {

        Path path = Paths.get(uploadDirectory);

        if (!Files.exists(path)) {
            Files.createDirectories(path);
        }

        try (InputStream inputStream = file.getInputStream()) {
            Path filePath = path.resolve(fileName);
            Files.copy(inputStream, filePath, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException ioe) {
            throw new IOException("Could not save image file: " + fileName, ioe);
        }

    }

    @Override
    public List<Image> findByStatusIsNull() {
        List<Image> images = imageRepository.findByStatusIsNull();
        return images;
 
    }

    @Override
    public List<Image> findByStatus(Boolean status) {
        List<Image> images = imageRepository.findByStatus(status);
        return images;
    }

    @Override
    public List<Image> findAllByCategoryAndStatus(Category c, Boolean status) {
        List<Image> images = imageRepository.findAllByCategoryAndStatus(c, status);
        return images;
    }

    @Override
    public void updateImageStatus(Image i, Boolean status) {
        Optional<Image> img = imageRepository.findById(i.getId());
        Image updatedImage = img.get();
        updatedImage.setId(i.getId());
        updatedImage.setStatus(status);
        imageRepository.save(updatedImage);
    }

    @Override
    public Image findById(Long id) {
        Image img = imageRepository.findById(id).get();
        return img;
    }

    @Override
    public Image saveImage(Image img) {
        imageRepository.save(img);
        return img;
    }
    
}
