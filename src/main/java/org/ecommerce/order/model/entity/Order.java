package org.ecommerce.order.model.entity;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.ecommerce.order.orderitem.model.entity.OrderItem;
import org.ecommerce.user.model.entity.User;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long orderId;
    @Enumerated(EnumType.STRING)
    private OrderStatus orderStatus;
    private LocalDate orderDate;
    private BigDecimal totalAmount;
    private BigDecimal totalPrice;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<OrderItem> orderItems = new HashSet<>();

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

}
