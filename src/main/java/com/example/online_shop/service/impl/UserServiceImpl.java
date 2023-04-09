package com.example.online_shop.service.impl;

import com.example.online_shop.dto.requestDto.UserRequestDto;
import com.example.online_shop.dto.responseDto.UserResponseDto;
import com.example.online_shop.entity.Order;
import com.example.online_shop.entity.Product;
import com.example.online_shop.entity.User;
import com.example.online_shop.exception.OrderNotFoundException;
import com.example.online_shop.repository.UserRepository;
import com.example.online_shop.service.OrderService;
import com.example.online_shop.service.UserService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    private final ProductServiceImpl productService;
    private final OrderService orderService;

    @Override
    public UserResponseDto addUser(UserRequestDto userRequestDto) {
        User user = new User();
        user.setFirstName(userRequestDto.getFirstName());
        user.setLastName(userRequestDto.getLastName());
        user.setEmail(userRequestDto.getEmail());
        user.setBalance(0D);
        user.setCart(new Order(user));
        user.setOrders(new ArrayList<>());
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
    public UserResponseDto editUser(Long userId, UserRequestDto userRequestDto) {
        User user = getUser(userId);
        user.setFirstName(userRequestDto.getFirstName());
        user.setLastName(userRequestDto.getLastName());
        user.setEmail(userRequestDto.getEmail());
        user.setBalance(userRequestDto.getBalance());
        return modelMapper.map(user, UserResponseDto.class);
    }

    @Override
    public UserResponseDto addOrderFromCart(Long userId) {
        User user = getUser(userId);
        Order cart = user.getCart();
        if (cart.getProducts().isEmpty()) {
            throw new IllegalArgumentException("The cart is empty, first you need to add products in it");
        }
        addNewOrder(user, cart);
        return modelMapper.map(user, UserResponseDto.class);
    }

    private void addNewOrder(User user, Order cart) {
        List<Order> orders = user.getOrders();
        double totalPrice = cart.getTotalPrice();
        double balance = user.getBalance();
        if (balance >= totalPrice) {
            Order tempOrder = new Order(user);
            tempOrder.addProducts(cart.getProducts());
            tempOrder.setUserCart(cart.getUserCart());
            orders.add(tempOrder);
            cart.getProducts().clear();
            user.setBalance(balance - totalPrice);
            orderService.saveOrder(tempOrder);
        } else throw new IllegalArgumentException("Not enough money. Top up balance");
    }

    @Transactional
    @Override
    public UserResponseDto removeOrderFromUser(Long userId, Long orderId) {
        User user = getUser(userId);
        Order order = orderService.getOrder(orderId);
        user.getOrders().remove(order);
        orderService.deleteOrder(orderId);
        return modelMapper.map(user, UserResponseDto.class);
    }

    @Transactional
    @Override
    public UserResponseDto addProductToCart(Long productId, int quantity, Long userId) {
        User user = getUser(userId);
        Product product = productService.getProduct(productId);
        user.getCart().addProduct(product, quantity);
        return modelMapper.map(user, UserResponseDto.class);
    }

    @Transactional
    @Override
    public UserResponseDto removeProductFromCart(Long productId, int quantity, Long userId) {
        User user = getUser(userId);
        Order cart = user.getCart();
        Product product = productService.getProduct(productId);
        int quantityInCart = user.getCart().getProducts().get(product);
        if (isProductInCart(cart, product)) {
            removeProduct(quantity, cart, product, quantityInCart);
        } else throw new IllegalArgumentException("There is no product: " + product.getName() + " in cart.");
        return modelMapper.map(user, UserResponseDto.class);
    }

    private boolean isProductInCart(Order cart, Product product) {
        return cart.getProducts().containsKey(product);
    }

    private static void removeProduct(int quantity, Order cart, Product product, int quantityInCart) {
        if (quantityInCart < quantity)
            throw new IllegalArgumentException("Not enough quantity of " + product.getName() + " in cart. " +
                                               "Cart has: " + quantityInCart + " and you want to remove: " + quantity);
        else if (quantityInCart == quantity) cart.getProducts().remove(product);
        else cart.getProducts().put(product, quantityInCart - quantity);
    }
}