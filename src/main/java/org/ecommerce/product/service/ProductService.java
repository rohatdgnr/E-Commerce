package org.ecommerce.product.service;


import lombok.RequiredArgsConstructor;
import org.ecommerce.category.model.entity.Category;
import org.ecommerce.category.repository.CategoryRepository;
import org.ecommerce.core.exceptions.ResourceAlreadyExistsException;
import org.ecommerce.core.exceptions.ResourceNotFoundException;
import org.ecommerce.image.model.dto.ImageDTO;
import org.ecommerce.image.model.entity.Image;
import org.ecommerce.image.repository.ImageRepository;
import org.ecommerce.product.model.dto.ProductDTO;
import org.ecommerce.product.model.dto.request.AddProductRequest;
import org.ecommerce.product.model.entity.Product;
import org.ecommerce.product.repository.ProductRepository;
import org.ecommerce.product.service.impl.IProductService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor // Generates a constructor with required arguments
public class ProductService implements IProductService {

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final ModelMapper modelMapper;
    private final ImageRepository imageRepository;


    @Override
    public Product addProduct(AddProductRequest request) {
        if (isProductExists(request.getName(), request.getBrand())) {
            throw new ResourceAlreadyExistsException(request.getBrand() + " "
                    + request.getName() + " already exists, you may update this product instead!");
        }
        Category category = Optional.ofNullable(categoryRepository.findByName(request.getCategory().getName()))
                .orElseGet(() -> {
                    Category newCategory = new Category(request.getCategory().getName());
                    return categoryRepository.save(newCategory);
                });
        request.setCategory(category);
        return productRepository.save(createProduct(request, category));
    }


    private boolean isProductExists(String name, String brand) {
        return productRepository.existsByNameAndBrand(name, brand);
    }

    private Product createProduct(AddProductRequest request, Category category) {
        return new Product(
                request.getName(),
                request.getBrand(),
                request.getDescription(),
                request.getPrice(),
                request.getInventory(),
                category
        );
    }

    @Override
    public Product getProductById(Long id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found when get product by id service!"));
    }

    @Override
    public void deleteProductById(Long id) {
        productRepository.findById(id)
                .ifPresentOrElse(productRepository::delete, () -> {
                    throw new ResourceNotFoundException("Product not found when delete product by id service!");
                });
    }

    @Override
    public Product updateProduct(Product product, Long id) {
        return Optional.ofNullable(getProductById(id)).map(oldProduct -> {
            oldProduct.setName(oldProduct.getName());
            return productRepository.save(oldProduct);
        }).orElseThrow(() -> new ResourceNotFoundException("Product not found when update product service!"));
    }

    @Override
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    @Override
    public List<Product> getProductsByCategory(String category) {
        return productRepository.findByCategoryName(category);
    }

    @Override
    public List<Product> getProductsByBrand(String brand) {
        return productRepository.findByBrand(brand);
    }

    @Override
    public List<Product> getProductsByCategoryAndBrand(String category, String brand) {
        return productRepository.findByCategoryNameAndBrand(category, brand);
    }

    @Override
    public List<Product> getProductsByBrandAndName(String brand, String name) {
        return productRepository.findByBrandAndName(brand, name);
    }

    @Override
    public Long countProductsByBrandAndName(String brand, String name) {
        return productRepository.countByBrandAndName(brand, name);
    }


    @Override
    public List<ProductDTO> getConvertedProducts(List<Product> products) {
        return products.stream().map(this::convertToDto).toList();
    }

    @Override
    public ProductDTO convertToDto(Product product) {
        ProductDTO productDto = modelMapper.map(product, ProductDTO.class);
        List<Image> images = imageRepository.findByProductId(product.getId());
        List<ImageDTO> imageDtos = images.stream().map(image -> modelMapper.map(image, ImageDTO.class)).toList();
        productDto.setImages(imageDtos);
        return productDto;
    }
}

