package org.ecommerce.order.orderitem.service;

import org.ecommerce.order.orderitem.repository.OrderItemRepository;
import org.ecommerce.order.orderitem.service.impl.IOrderItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrderItemService implements IOrderItemService {

    OrderItemRepository orderItemRepository;

    @Autowired
    public OrderItemService(OrderItemRepository orderItemRepository) {
        this.orderItemRepository = orderItemRepository;
    }
}
