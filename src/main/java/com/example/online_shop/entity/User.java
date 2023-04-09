package com.example.online_shop.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
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
    @NotBlank(message = "First name of user is required field.")
    private String firstName;

    @Column(name = "last_name", nullable = false)
    @NotBlank(message = "Last name of user is required field.")
    private String lastName;

    @Column(name = "email", nullable = false)
    @Pattern(regexp = "\\w+@\\w+\\.\\D{2,3}", message = "please use pattern hello@gmail.com")
    private String email;

    @Column(name = "balance", nullable = false)
    @Min(value = 0, message = "Balance must be bigger than -1.")
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
}
