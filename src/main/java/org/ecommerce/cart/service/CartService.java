package org.ecommerce.cart.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.ecommerce.cart.cartitem.repository.CartItemRepository;
import org.ecommerce.cart.model.dto.CartDTO;
import org.ecommerce.cart.model.entity.Cart;
import org.ecommerce.cart.repository.CartRepository;
import org.ecommerce.cart.service.impl.ICartService;
import org.ecommerce.core.exceptions.ResourceNotFoundException;
import org.ecommerce.user.model.entity.User;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CartService implements ICartService {

    private final CartItemRepository cartItemRepository;
    private CartRepository cartRepository;
    private ModelMapper modelMapper;

    @Autowired
    public CartService(CartRepository cartRepository, CartItemRepository cartItemRepository){
        this.cartRepository = cartRepository;
        this.cartItemRepository = cartItemRepository;
    }


    @Override
    public Cart getCartById(Long id) {
        Cart cart = cartRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Cart not found when get cart service!"));
        BigDecimal totalAmount = cart.getTotalAmount();
        cart.setTotalAmount(totalAmount);
        return cartRepository.save(cart);
    }

    @Override
    public Cart getCartByUserId(Long userId) {
        return cartRepository.findByUserId(userId);
    }


    @Transactional // This annotation is used to ensure that the transaction is completed successfully.
    @Override
    public void clearCart(Long id) {
        Cart cart = cartRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Cart not found when clear cart service!"));



        cartItemRepository.deleteAllByCartId(id);
        cart.getItems().clear();
        cart.setTotalAmount(BigDecimal.ZERO);
        cart.setTotalPrice(BigDecimal.ZERO);
        cartRepository.deleteById(id);
    }

    @Override
    public BigDecimal getTotalPrice(Long cartId) {
        return cartRepository.findById(cartId).orElseThrow(() -> new ResourceNotFoundException("Cart not found!")).getTotalPrice();
    }

    @Override
    public Cart createCart(User user) {
        return Optional.ofNullable(getCartByUserId(user.getId()))
                .orElseGet(() -> {
                    Cart cart = new Cart();
                    cart.setUser(user);
                    return cartRepository.save(cart);
                });
    }

    @Override
    public CartDTO convertToDto(Cart cart){
        return modelMapper.map(cart, CartDTO.class);
    }



}
