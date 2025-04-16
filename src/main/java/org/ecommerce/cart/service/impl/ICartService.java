package org.ecommerce.cart.service.impl;

import org.ecommerce.cart.model.dto.CartDTO;
import org.ecommerce.cart.model.entity.Cart;
import org.ecommerce.user.model.entity.User;

import java.math.BigDecimal;

public interface ICartService {
    Cart getCartByUserId(Long id);
    Cart getCartById(Long id);
    void clearCart(Long id);
    BigDecimal getTotalPrice(Long cartId);
    Cart createCart(User user);

    CartDTO convertToDto(Cart cart);
}
