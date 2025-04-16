package org.ecommerce.image.service.impl;

import org.ecommerce.image.model.dto.ImageDTO;
import org.ecommerce.image.model.entity.Image;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface IImageService {

    List<ImageDTO> addImages(List<MultipartFile> file, Long productId);

    void deleteImageById(Long id);

    void updateImage(MultipartFile file, Long imageId);

    Image getImageById(Long id);

}
