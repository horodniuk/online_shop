package com.example.online_shop.service.impl;

import com.example.online_shop.dto.requestDto.ProductRequestDto;
import com.example.online_shop.dto.requestDto.UserRequestDto;
import com.example.online_shop.dto.responseDto.UserResponseDto;
import com.example.online_shop.entity.Order;
import com.example.online_shop.entity.User;
import com.example.online_shop.exception.NoSuchUserException;
import com.example.online_shop.repository.UserRepository;
import com.example.online_shop.service.UserService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;

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
        Optional<User> optionalUser = userRepository.findById(userId);
        if (optionalUser.isEmpty()) {
            throw new NoSuchUserException("There is no user with id: " + userId + " in DB.");
        }
        User user = optionalUser.get();
        return modelMapper.map(user, UserResponseDto.class);
    }

    @Override
    public User getUser(Long userId) {
        Optional<User> optionalUser = userRepository.findById(userId);
        if (optionalUser.isEmpty()) {
            throw new NoSuchUserException("There is no user with id: " + userId + " in DB.");
        }
        return optionalUser.get();
    }

    @Override
    public UserResponseDto deleteUser(Long userId) {
        User user = getUser(userId);
        userRepository.delete(user);
        return modelMapper.map(user, UserResponseDto.class);
    }

    @Override
    public UserResponseDto editUser(Long userId, UserRequestDto userRequestDto) {
        return null;
    }

    @Override
    public UserResponseDto addOrderFromCart(Long userId) {
        return null;
    }

    @Override
    public UserResponseDto addProductToCart(ProductRequestDto productRequestDto, int quantity) {
        return null;
    }

    @Override
    public UserResponseDto removeProductFromCart(ProductRequestDto productRequestDto, int quantity) {
        return null;
    }
}
