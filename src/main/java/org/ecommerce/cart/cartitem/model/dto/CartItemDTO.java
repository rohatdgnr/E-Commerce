package org.ecommerce.cart.cartitem.model.dto;

import lombok.Data;
import org.ecommerce.product.model.entity.Product;

import java.math.BigDecimal;

@Data
public class CartItemDTO {
    private Long id;
    private Integer quantity;
    private Product product;
    private BigDecimal unitPrice;

}
