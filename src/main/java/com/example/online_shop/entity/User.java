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

    public User(String firstName, String lastName, String email, Double balance) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.balance = balance;
        this.orders = new ArrayList<>();
        this.cart = new Order();
        this.cart.setUserCart(this);
        this.orders.add(this.cart);
    }

    public User() {
        this.orders = new ArrayList<>();
        this.cart = new Order();
        this.cart.setUserCart(this);
        this.orders.add(this.cart);
    }

    public void addToCart(Product product, int quantity) {
        cart.addProduct(product, quantity);
    }

    public Order addOrderFromCart() {
        double totalPrice = cart.getTotalPrice();
        if (balance >= totalPrice) {
            Order tempOrder = new Order(this);
            tempOrder.addProducts(cart.getProducts());
            tempOrder.setUserCart(cart.getUserCart());
            orders.add(tempOrder);
            cart.getProducts().clear();
            balance -= totalPrice;
            return tempOrder;
        } else {
            throw new IllegalArgumentException("Not enough money. Top up balance");
        }
    }
}
