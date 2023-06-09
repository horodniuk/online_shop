package com.example.online_shop.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.catalina.UserDatabase;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;


@Entity
@Data
@NoArgsConstructor
@Table(name = "users")
public class User implements UserDetails {
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
    @Pattern(regexp = "\\w+@\\w+\\.[a-z]{2,3}", message = "please use pattern hello@gmail.com")
    private String email;

    @Column(name = "password", nullable = false)
    @NotBlank(message = "Password is required field")
    private String password;

    @Column(name = "role", nullable = false)
    @Enumerated(value = EnumType.STRING)
    private Role role;

    @Column(name = "balance", nullable = false)
    @Min(value = 0, message = "Balance must be bigger than -1.")
    private Double balance;

    @Column(name = "is_blocked", nullable = false)
    private boolean isBlocked;

    @OneToMany(mappedBy = "user")
    private List<Order> orders = new ArrayList<>();

    public void block() {
        if (this.role == Role.ADMIN) {
            this.isBlocked = true;
        }
    }

    public void unblock() {
        if (this.role == Role.ADMIN) {
            this.isBlocked = false;
        }
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singleton(this.getRole());
    }

    @Override
    public String getUsername() {
         return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return !isBlocked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
