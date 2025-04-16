package org.ecommerce.product.service.impl;


import org.ecommerce.product.model.dto.ProductDTO;
import org.ecommerce.product.model.dto.request.AddProductRequest;
import org.ecommerce.product.model.entity.Product;

import java.util.List;

public interface IProductService {
    void deleteProductById(Long id);

    Product updateProduct(Product product, Long productId);

    Product addProduct(AddProductRequest request);

    Product getProductById(Long id);

    List<Product> getAllProducts();

    List<Product> getProductsByCategory(String category);

    List<Product> getProductsByBrand(String brand);

    List<Product> getProductsByCategoryAndBrand(String category, String brand);

    List<Product> getProductsByBrandAndName(String brand, String name);

    Long countProductsByBrandAndName(String brand, String name);

    ProductDTO convertToDto(Product product);

    List<ProductDTO> getConvertedProducts(List<Product> products);
}

