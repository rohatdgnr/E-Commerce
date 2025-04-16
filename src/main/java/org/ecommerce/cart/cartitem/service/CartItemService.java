package org.ecommerce.cart.cartitem.service;

import org.ecommerce.cart.cartitem.model.entity.CartItem;
import org.ecommerce.cart.cartitem.repository.CartItemRepository;
import org.ecommerce.cart.cartitem.service.impl.ICartItemService;
import org.ecommerce.cart.model.entity.Cart;
import org.ecommerce.cart.repository.CartRepository;
import org.ecommerce.cart.service.impl.ICartService;
import org.ecommerce.core.exceptions.ResourceNotFoundException;
import org.ecommerce.product.model.entity.Product;
import org.ecommerce.product.service.impl.IProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CartItemService implements ICartItemService {

    private final CartRepository cartRepository;
    private final CartItemRepository cartItemRepository;
    private final IProductService iProductService;
    private final ICartService iCartService;

    @Autowired
    public CartItemService(CartRepository cartRepository, CartItemRepository cartItemRepository, IProductService iProductService, ICartService iCartService) {
        this.cartRepository = cartRepository;
        this.cartItemRepository = cartItemRepository;
        this.iProductService = iProductService;
        this.iCartService = iCartService;
    }


    @Override
    public void addCartItem(Long cartId, Long productId, int quantity) {
        // Get cart
        // Get product
        // Check if product is already in cart
        // If it is, update quantity
        // If it is not, add new cart item
        Cart cart = iCartService.getCartById(cartId);
        Product product = iProductService.getProductById(productId);
        CartItem cartItem = cart.getItems().stream().filter(item ->
                        item.getProduct().getId().equals(productId))
                .findFirst().orElse(new CartItem());

        if (cartItem.getId() == null) {
            cartItem.setCart(cart);
            cartItem.setProduct(product);
            cartItem.setQuantity(quantity);
            cartItem.setUnitPrice(product.getPrice());
        } else {
            cartItem.setQuantity(cartItem.getQuantity() + quantity);
        }
        cartItem.setTotalPrice();
        cart.addItem(cartItem);
        cartItemRepository.save(cartItem);
        cartRepository.save(cart);
    }

    @Override
    public void removeCartItem(Long cartId, Long productId) {
        Cart cart = iCartService.getCartById(cartId);
        CartItem cartItem = cart.getItems().stream().filter(item ->
                        item.getProduct().getId().equals(productId))
                .findFirst().orElseThrow(() -> new ResourceNotFoundException("Product not found in cart!"));
        cart.removeItem(cartItem);
        cartItemRepository.delete(cartItem);
        cartRepository.save(cart);
    }

    @Override
    public void updateCartItemQuantity(Long cartId, Long productId, int quantity) {
        Cart cart = iCartService.getCartById(cartId);
        CartItem cartItem = cart.getItems().stream().filter(item ->
                        item.getProduct().getId().equals(productId))
                .findFirst().orElseThrow(() -> new ResourceNotFoundException("Product not found in cart!"));
        cartItem.setQuantity(quantity);
        cartItem.setTotalPrice();

        cart.calculateTotalPrice();
        cart.calculateTotalAmount();

        cartItemRepository.save(cartItem);

    }
}

