package org.ecommerce.category.model.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.ecommerce.product.model.entity.Product;

import java.util.List;

public class CategoryDTO {  //Todo change to record
    private Long id;
    private String name;
    @JsonIgnore
    private List<Product> products;
}
