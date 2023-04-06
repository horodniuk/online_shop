package com.example.online_shop.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


@Entity
@Data
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long userId;

    @Column(name = "first_name", nullable = false)
    private String firstName;

    @Column(name = "last_name", nullable = false)
    private String lastName;

    @Column(name = "email", nullable = false)
    private String email;

    @Column(name = "balance", nullable = false)
    private Double balance;

    @OneToMany(mappedBy = "user")
    private List<Order> orders;

    @OneToOne(mappedBy = "userCart")
    private Order cart;

    public Order addOrderFromCart() {
        double totalPrice = cart.getTotalPrice();
        if (balance >= totalPrice) {
            Order tempOrder = new Order(this);
            tempOrder.addProducts(cart.getProducts());
            tempOrder.setUserCart(cart.getUserCart());
            this.orders.add(tempOrder);
            this.cart.getProducts().clear();
            balance -= totalPrice;
            return tempOrder;
        } else {
            throw new IllegalArgumentException("Not enough money. Top up balance");
        }
    }
}
