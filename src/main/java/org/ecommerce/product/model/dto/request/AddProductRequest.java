package org.ecommerce.product.model.dto.request;

import lombok.Data;
import org.ecommerce.category.model.entity.Category;

import java.math.BigDecimal;

@Data
public class AddProductRequest {
    private Long id;
    private String name;
    private String brand;
    private BigDecimal price;
    private int inventory;
    private String description;
    private Category category;
}
