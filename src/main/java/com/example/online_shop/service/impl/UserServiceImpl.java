package com.example.online_shop.service.impl;

import com.example.online_shop.dto.requestDto.CartRequestDto;
import com.example.online_shop.dto.requestDto.UserRequestDto;
import com.example.online_shop.dto.responseDto.OrderResponseDto;
import com.example.online_shop.dto.responseDto.UserResponseDto;
import com.example.online_shop.entity.Order;
import com.example.online_shop.entity.Product;
import com.example.online_shop.entity.Role;
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
    private final ProductServiceImpl productService;
    private final OrderService orderService;


    @Transactional
    @Override
    public UserResponseDto addProductToCart(CartRequestDto cartDto) {
        User user = getUser(cartDto.getUserId());
        Product product = productService.getById(cartDto.getProductId());
        user.addProductToCart(product, cartDto.getQuantity());
        return modelMapper.map(user, UserResponseDto.class);
    }

    @Transactional
    @Override
    public UserResponseDto removeProductFromCart(CartRequestDto cartDto) {
        User user = getUser(cartDto.getUserId());
        Product product = productService.getById(cartDto.getProductId());
        user.removeProductFromCart(product, cartDto.getQuantity());
        System.out.println(user.getCart());
        return modelMapper.map(user, UserResponseDto.class);
    }

    @Override
    public UserResponseDto addOrderToUser(Long userId) {
        User user = getUser(userId);
        Map<Product, Integer> cart = user.getCart();
        if (cart.isEmpty()) {
            throw new IllegalArgumentException("The cart is empty, first you need to add products in it");
        }
        addNewOrder(user, cart);
        return modelMapper.map(user, UserResponseDto.class);
    }

    private void addNewOrder(User user, Map<Product, Integer> cart) {
        List<Order> orders = user.getOrders();
        double totalPrice = cart.entrySet().stream()
                .mapToDouble(entry -> entry.getKey().getPrice() * entry.getValue())
                .sum();
        double balance = user.getBalance();
        if (balance >= totalPrice) {
            Order newOrder = new Order(user);
            orders.add(newOrder);
            user.setBalance(balance - totalPrice);
            orderService.saveOrder(newOrder);
            user.setCart(new HashMap<>());
        } else throw new IllegalArgumentException("Not enough money. Top up balance");
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
    public UserResponseDto removeOrderFromUser(Long userId, Long orderId) {
        User user = getUser(userId);
        Order order = orderService.getOrder(orderId);
        user.getOrders().remove(order);
        orderService.deleteOrder(orderId);
        return modelMapper.map(user, UserResponseDto.class);
    }





    private boolean isProductInCart(Order cart, Product product) {
        return cart.getProducts().containsKey(product);
    }

    private void removeProduct(int quantity, Order cart, Product product, int quantityInCart) {
        if (quantityInCart < quantity)
            throw new IllegalArgumentException("Not enough quantity of " + product.getName() + " in cart. " +
                                               "Cart has: " + quantityInCart + " and you want to remove: " + quantity);
        else if (quantityInCart == quantity) cart.getProducts().remove(product);
        else cart.getProducts().put(product, quantityInCart - quantity);
    }

    @Override
    public UserResponseDto clearCart(Long userId) {
        User user = getUser(userId);
        user.getCart().clear();
        return modelMapper.map(user, UserResponseDto.class);
    }

    @Override
    public Map<Product, Integer> showAllProductsInCart(Long userId) {
        User user = getUser(userId);
        return user.getCart();
    }

    @Override
    public List<OrderResponseDto> getAllUserOrders(Long userId) {
        User user = getUser(userId);
        return user.getOrders().stream()
                .map(order -> modelMapper.map(order, OrderResponseDto.class)).toList();
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
}
