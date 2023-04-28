package com.example.online_shop.service.impl;

import com.example.online_shop.dto.requestDto.OrderRequestDto;
import com.example.online_shop.dto.requestDto.OrderInfoRequestDto;
import com.example.online_shop.dto.requestDto.UserRequestDto;
import com.example.online_shop.dto.responseDto.UserResponseDto;
import com.example.online_shop.entity.Order;
import com.example.online_shop.entity.Product;
import com.example.online_shop.entity.Role;
import com.example.online_shop.entity.User;
import com.example.online_shop.exception.OrderNotFoundException;
import com.example.online_shop.repository.OrderRepository;
import com.example.online_shop.repository.ProductRepository;
import com.example.online_shop.repository.UserRepository;
import com.example.online_shop.service.UserService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.example.online_shop.entity.Role.ADMIN;
import static com.example.online_shop.entity.Role.USER;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    private final ProductRepository productRepository;
    private final OrderRepository orderRepository;


    @Override
    public UserResponseDto addOrder(OrderRequestDto orderRequestDto) {
        User user = userRepository.findById(orderRequestDto.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found with id: " + orderRequestDto.getUserId()));

        double totalPrice = getTotalPrice(orderRequestDto);

        if (totalPrice > user.getBalance()) {
            throw new RuntimeException("User does not have enough balance.");
        }

        Order order = new Order(user);
        Map<Product, Integer> products = putProductToOrder(orderRequestDto);
        order.setProducts(products);
        orderRepository.save(order);

        user.setBalance(user.getBalance() - totalPrice);
        userRepository.save(user);

        return modelMapper.map(user, UserResponseDto.class);
    }

    @Override
    public UserResponseDto removeOrder(OrderInfoRequestDto orderRequestDto) {
        User user = userRepository.findById(orderRequestDto.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found with id: " + orderRequestDto.getUserId()));

        Order order = user.getOrders().stream()
                .filter(o -> o.getOrderId().equals(orderRequestDto.getOrderId()))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Order not found with id: " + orderRequestDto.getOrderId()));

        Map<Long, Integer> products = orderRequestDto.getProducts();
        removeProductToOrder(order, products);
        updateBalanceAfterRefund(user, order);
        userRepository.save(user);

        return modelMapper.map(user, UserResponseDto.class);
    }

    private void updateBalanceAfterRefund(User user, Order order) {
        double orderPrice = order.getProducts().entrySet().stream()
                .mapToDouble(entry -> entry.getKey().getPrice() * entry.getValue())
                .sum();
        user.setBalance(user.getBalance() + orderPrice);
    }


    @Override
    public UserResponseDto createUser(UserRequestDto userRequestDto) {
        return setUserDetails(userRequestDto, USER);
    }

    @Override
    public UserResponseDto createAdmin(UserRequestDto userRequestDto) {
        return setUserDetails(userRequestDto, ADMIN);
    }

    private UserResponseDto setUserDetails(UserRequestDto userRequestDto, Role role) {
        User user = new User();
        user.setFirstName(userRequestDto.getFirstName());
        user.setLastName(userRequestDto.getLastName());
        user.setEmail(userRequestDto.getEmail());
        user.setPassword(userRequestDto.getPassword());
        user.setRole(role);
        user.setBalance(0D);
        userRepository.save(user);
        return modelMapper.map(user, UserResponseDto.class);
    }

    @Override
    public List<UserResponseDto> getAllUsers() {
        return userRepository.findAll().stream()
                .map(user -> modelMapper.map(user, UserResponseDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public UserResponseDto getUserById(Long userId) {
        User user = getUser(userId);
        return modelMapper.map(user, UserResponseDto.class);
    }

    @Override
    public User getUser(Long userId) {
        return userRepository.findById(userId).orElseThrow(
                () -> new OrderNotFoundException("Order with id " + userId + " not found!"));
    }

    @Transactional
    @Override
    public UserResponseDto deleteUser(Long userId) {
        User user = getUser(userId);
        userRepository.delete(user);
        return modelMapper.map(user, UserResponseDto.class);
    }

    @Transactional
    @Override
    public UserResponseDto editUser(UserRequestDto userRequestDto) {
        User user = getUser(userRequestDto.getUserId());
        user.setFirstName(userRequestDto.getFirstName());
        user.setLastName(userRequestDto.getLastName());
        user.setEmail(userRequestDto.getEmail());
        return modelMapper.map(user, UserResponseDto.class);
    }

    @Transactional
    @Override
    public UserResponseDto changeUserToAdmin(Long userId) {
        User user = getUser(userId);
        user.setRole(ADMIN);
        return modelMapper.map(user, UserResponseDto.class);
    }

    @Transactional
    @Override
    public String addBalance(UserRequestDto userRequestDto) {
        Long userId = userRequestDto.getUserId();
        User user = getUser(userId);
        Double balance = user.getBalance();
        Double sumToAdd = userRequestDto.getBalance();

        if (sumToAdd > 0) {
            user.setBalance(balance + sumToAdd);
        } else throw new IllegalArgumentException("The sum to replenish the balance must be greater than null! " +
                                               "Sum you entered is: " + sumToAdd);
        Double newBalance = user.getBalance();
        return "New balance of user " + userId + " is: " + newBalance;
    }

    private double getTotalPrice(OrderRequestDto orderRequestDto) {
        return orderRequestDto.getProducts().entrySet().stream()
                .mapToDouble(entry -> {
                    Product product = productRepository
                            .findById(entry.getKey())
                            .orElseThrow(() -> new RuntimeException("Product not found with id: " + entry.getKey()));
                    return product.getPrice() * entry.getValue();
                })
                .sum();
    }



    private Map<Product, Integer> putProductToOrder(OrderRequestDto orderRequestDto) {
        Map<Product, Integer> products = new HashMap<>();
        for (Map.Entry<Long, Integer> entry : orderRequestDto.getProducts().entrySet()) {
            Product product = productRepository
                    .findById(entry.getKey())
                    .orElseThrow(() -> new RuntimeException("Product not found with id: " + entry.getKey()));
            products.put(product, entry.getValue());
        }
        return products;
    }

    private void removeProductToOrder(Order order, Map<Long, Integer> products) {
        for (Map.Entry<Long, Integer> entry : products.entrySet()) {
            Long productId = entry.getKey();
            Integer quantity = entry.getValue();
            Product product = productRepository
                    .findById(productId)
                    .orElseThrow(() -> new RuntimeException("Product not found with id: " + productId));
            order.removeProducts(product, quantity);
        }
    }

}
