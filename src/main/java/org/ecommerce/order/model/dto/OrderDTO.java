package org.ecommerce.order.model.dto;

import lombok.Data;
import org.ecommerce.order.orderitem.model.dto.OrderItemDTO;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Data
public class OrderDTO {
    private Long orderId;
    private Long userId;
    private String orderStatus;
    private LocalDate orderDate;
    private BigDecimal totalAmount;
    private BigDecimal totalPrice;
    private List<OrderItemDTO> items;


}

