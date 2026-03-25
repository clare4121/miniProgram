package com.minprogram.image.service;

import com.minprogram.image.entity.Image;
import com.minprogram.image.repository.ImageRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ImageService {

    private final ImageRepository imageRepository;

    public ImageService(ImageRepository imageRepository) {
        this.imageRepository = imageRepository;
    }

    public Image saveImage(String imageUrl, Integer targetType, Long targetId) {
        Image image = new Image();
        image.setImageUrl(imageUrl);
        image.setTargetType(targetType);
        image.setTargetId(targetId);
        return imageRepository.save(image);
    }

    public List<Image> getImagesByTarget(Integer targetType, Long targetId) {
        return imageRepository.findByTargetTypeAndTargetId(targetType, targetId);
    }

    public Image getImageById(Long id) {
        return imageRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("图片不存在"));
    }

    public void deleteImage(Long id) {
        Image image = getImageById(id);
        image.setStatus(0);
        imageRepository.save(image);
    }
}