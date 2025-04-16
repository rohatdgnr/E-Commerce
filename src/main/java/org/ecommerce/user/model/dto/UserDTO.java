package org.ecommerce.user.model.dto;

import lombok.Data;
import org.ecommerce.cart.model.dto.CartDTO;
import org.ecommerce.order.model.entity.Order;

import java.util.List;
@Data
public class UserDTO {
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private List<Order> orders;
    private CartDTO cart;
}
