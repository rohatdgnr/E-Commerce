package org.ecommerce.cart.model.dto;

import lombok.Data;
import org.ecommerce.cart.cartitem.model.dto.CartItemDTO;

import java.math.BigDecimal;
import java.util.Set;

@Data
public class CartDTO {
    private Long id;
    private BigDecimal totalAmount;
    private BigDecimal totalPrice;
    private Set<CartItemDTO> items;
}

