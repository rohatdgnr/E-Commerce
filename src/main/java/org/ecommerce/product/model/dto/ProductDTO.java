package org.ecommerce.product.model.dto;

import lombok.Getter;
import lombok.Setter;
import org.ecommerce.category.model.entity.Category;
import org.ecommerce.image.model.dto.ImageDTO;

import java.math.BigDecimal;
import java.util.List;

@Setter
@Getter
public class ProductDTO{ //Todo change to record
    private Long id;
    private String name;
    private String brand;
    private BigDecimal price;
    private int inventory;
    private String description;
    private Category category;
    private List<ImageDTO> images;
}
