package org.ecommerce.order.controller;


import org.ecommerce.core.exceptions.ResourceNotFoundException;
import org.ecommerce.core.utils.api.ApiResponse;
import org.ecommerce.order.model.dto.OrderDTO;
import org.ecommerce.order.model.entity.Order;
import org.ecommerce.order.service.impl.IOrderService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("${api.prefix}/orders")
@RestController
public class OrderController {

    private final IOrderService orderService;

    public OrderController(IOrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping("/{userId}/all")
    public ResponseEntity<ApiResponse> getOrders(@PathVariable Long userId) {
        try{
            List<OrderDTO> orders = orderService.getUserOrders(userId);
            return ResponseEntity.ok(new ApiResponse("Get all orders request success!", orders));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new ApiResponse(e.getMessage(), null));
        }
    }

    @GetMapping("/{orderId}")
    public ResponseEntity<ApiResponse> getOrder(@PathVariable Long orderId) {
        try{
            OrderDTO order = orderService.getOrderById(orderId);
            return ResponseEntity.ok(new ApiResponse("Get order request success!", order));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.badRequest().body(new ApiResponse(e.getMessage(), null));
        }
    }

    @DeleteMapping("/{orderId}")
    public ResponseEntity<ApiResponse> cancelOrder(@PathVariable Long orderId) {
        try{
            orderService.cancelOrder(orderId);
            return ResponseEntity.ok(new ApiResponse("Order cancelled successfully!", null));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.badRequest().body(new ApiResponse(e.getMessage(), null));
        }
    }

    @PostMapping("/{userId}")
    public ResponseEntity<ApiResponse> placeOrder(@PathVariable Long userId) {
        try{
            Order order = orderService.placeOrder(userId);
            OrderDTO orderDto =  orderService.convertToDto(order);
            return ResponseEntity.ok(new ApiResponse("Order placed successfully!", orderDto));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse("An error occured", e.getMessage()));
        }
    }

}

