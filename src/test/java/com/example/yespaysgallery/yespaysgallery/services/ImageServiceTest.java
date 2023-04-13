package com.example.yespaysgallery.yespaysgallery.services;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.example.yespaysgallery.model.Image;
import com.example.yespaysgallery.repository.ImageRepository;
import com.example.yespaysgallery.services.ImageServiceImp;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;


@ExtendWith(MockitoExtension.class)
public class ImageServiceTest {

    @Mock
    private ImageRepository imageRepository;

    @InjectMocks
    private ImageServiceImp imgService;

    private Image img1, img2, img3;

    @BeforeEach
    public void setup(){
        img1 = new Image(1l,"/image/someImage/1","My first image", true);
        img2 = new Image(2l,"/image/someImage/2","My second image", true);        
        img3 = new Image(3l,"/image/someImage/3","My third image", null);        

    }


    @Test
    @DisplayName("JUnit test for add Image")
    public void shouldAddImage(){
        given(imageRepository.save(img1)).willReturn(img1);
        Image savedImage = imgService.saveImage(img1);      ;
        assertThat(savedImage).isNotNull();
    }

    @Test
    @DisplayName("JUnit test for displays Images")
    @Transactional
    public void shouldDisplayOnlyApprovedImage(){
        given(imageRepository.findByStatus(true)).willReturn(List.of(img1,img2));
        List<Image> imgList = imgService.findByStatus(true);

        assertThat(imgList).isNotNull();
        assertThat(imgList.size()).isEqualTo(2);
        
    }

    @Test
    @DisplayName("JUnit test for displays Orders")
    @Transactional
    public void shouldDisplayOrders(){
        given(imageRepository.findByStatusIsNull()).willReturn(List.of(img3));
        List<Image> imgList = imgService.findByStatusIsNull();

        assertThat(imgList).isNotNull();
        assertThat(imgList.size()).isEqualTo(1);
        
    }

    @DisplayName("JUnit test for view Image")
    @Test
    public void shouldViewImagee(){
        // given
        given(imageRepository.findById(1L)).willReturn(Optional.of(img1));

        // when
        imgService.saveImage(img1);
        Image savedImage = imgService.findById(img1.getId());

        // then
        assertThat(savedImage).isNotNull();

    }

  
    
}
