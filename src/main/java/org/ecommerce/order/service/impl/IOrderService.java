package org.ecommerce.order.service.impl;

import org.ecommerce.order.model.dto.OrderDTO;
import org.ecommerce.order.model.entity.Order;

import java.util.List;

public interface IOrderService {
    Order placeOrder(Long userId);
    OrderDTO getOrderById(Long orderId);
    void cancelOrder(Long orderId);
    List<OrderDTO> getUserOrders(Long userId);

    OrderDTO convertToDto(Order order);
}
