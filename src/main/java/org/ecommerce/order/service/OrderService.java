package org.ecommerce.order.service;


import jakarta.transaction.Transactional;
import org.ecommerce.cart.model.entity.Cart;
import org.ecommerce.cart.service.CartService;
import org.ecommerce.core.exceptions.ResourceNotFoundException;
import org.ecommerce.order.model.dto.OrderDTO;
import org.ecommerce.order.model.entity.Order;
import org.ecommerce.order.model.entity.OrderStatus;
import org.ecommerce.order.orderitem.model.entity.OrderItem;
import org.ecommerce.order.repository.OrderRepository;
import org.ecommerce.order.service.impl.IOrderService;
import org.ecommerce.product.model.entity.Product;
import org.ecommerce.product.repository.ProductRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;

@Service
public class OrderService implements IOrderService {

    private final ProductRepository productRepository;
    private final CartService cartService;
    private final OrderRepository orderRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public OrderService(OrderRepository orderRepository, ProductRepository productRepository, CartService cartService, ModelMapper modelMapper){
        this.orderRepository = orderRepository;
        this.productRepository = productRepository;
        this.cartService = cartService;
        this.modelMapper = modelMapper;
    }


    public Order createOrder(Cart cart){
        Order order = new Order();
        order.setUser(cart.getUser());
        order.setOrderStatus(OrderStatus.PENDING);
        order.setOrderDate(LocalDate.now());
        return order;
    }


    public List<OrderItem> createOrderItems(Order order, Cart cart ) {
        return cart.getItems().stream().map(cartItem -> {
            Product product = cartItem.getProduct();
            product.setInventory(product.getInventory() - cartItem.getQuantity());
            productRepository.save(product);
            return new OrderItem(
                    order,
                    product,
                    cartItem.getQuantity(),
                    cartItem.getUnitPrice()
            );
        }).toList();
    }

    @Transactional
    @Override
    public Order placeOrder(Long userId) {
        Cart cart = cartService.getCartByUserId(userId);
        Order order = createOrder(cart);
        List<OrderItem> orderItemList = createOrderItems(order, cart);
        order.setOrderItems(new HashSet<>(orderItemList));

        order.setTotalAmount(cart.getTotalAmount());
        order.setTotalPrice(cart.getTotalPrice());
        Order savedOrder = orderRepository.save(order);
        cartService.clearCart(userId);
        return savedOrder;
    }

    @Override
    public OrderDTO getOrderById(Long orderId) {
        return orderRepository.findById(orderId).map(this::convertToDto).orElseThrow(() -> new ResourceNotFoundException("Order not found when get order by id service!"));
    }

    @Override
    public void cancelOrder(Long orderId) {
        Order order = orderRepository.findById(orderId).orElseThrow(() -> new ResourceNotFoundException("Order not found when cancel order service!"));
        order.setOrderStatus(OrderStatus.CANCELED);
        orderRepository.save(order);
    }

    @Override
    public List<OrderDTO> getUserOrders(Long userId){
        return orderRepository.findAllByUserId(userId).stream().map(this::convertToDto).toList();
    }

    @Override
    public OrderDTO convertToDto(Order order){
        return modelMapper.map(order, OrderDTO.class);
    }
}

