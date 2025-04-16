package org.ecommerce.image.service;


import lombok.RequiredArgsConstructor;
import org.ecommerce.core.exceptions.ImageException;
import org.ecommerce.core.exceptions.ResourceNotFoundException;
import org.ecommerce.image.model.dto.ImageDTO;
import org.ecommerce.image.model.entity.Image;
import org.ecommerce.image.repository.ImageRepository;
import org.ecommerce.image.service.impl.IImageService;
import org.ecommerce.product.model.entity.Product;
import org.ecommerce.product.service.impl.IProductService;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.sql.rowset.serial.SerialBlob;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ImageService implements IImageService {

    private final ImageRepository imageRepository;
    private final IProductService iProductService;

    @Override
    public List<ImageDTO> addImages(List<MultipartFile> files, Long productId) {
        Product product = iProductService.getProductById(productId);
        List<ImageDTO> savedImageDto = new ArrayList<>();

        for (MultipartFile file : files) {
            try {
                Image image = new Image();
                image.setFileName(file.getOriginalFilename());
                image.setFileType(file.getContentType());
                image.setBlob(new SerialBlob(file.getBytes()));
                image.setProduct(product);

                Image savedImage = imageRepository.save(image);

                String buildDownloadUrl = "/api/v1/images/image/download/";
                String downloadUrl = buildDownloadUrl + savedImage.getId();
                savedImage.setDownloadUrl(downloadUrl);
                imageRepository.save(savedImage);

                ImageDTO imageDto = new ImageDTO();
                imageDto.setId(savedImage.getId());
                imageDto.setFileName(savedImage.getFileName());
                imageDto.setDownloadUrl(savedImage.getDownloadUrl());


                savedImageDto.add(imageDto);

            } catch (IOException | SQLException e) {
                throw new ImageException(e.getMessage());
            }
        }
        return savedImageDto;
    }

    @Override
    public void deleteImageById(Long id) {
        imageRepository.findById(id)
                .ifPresentOrElse(imageRepository::delete,
                        () -> {
                            throw new ResourceNotFoundException("Image not found!");
                        });
    }

    @Override
    public void updateImage(MultipartFile file, Long imageId) {
        Image image = getImageById(imageId);
        try {
            image.setFileName(file.getOriginalFilename());

            image.setBlob(new SerialBlob(file.getBytes()));
            imageRepository.save(image);
        } catch (IOException | SQLException e) {
            throw new ImageException(e.getMessage());
        }
    }

    @Override
    public Image getImageById(Long id) {
        return imageRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Image not found!"));
    }
}
