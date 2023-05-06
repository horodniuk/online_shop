package com.example.online_shop.controller;

import com.example.online_shop.dto.requestDto.UserRequestDto;
import com.example.online_shop.dto.responseDto.UserInfoResponseDto;
import com.example.online_shop.dto.responseDto.UserResponseDto;
import com.example.online_shop.entity.Role;
import com.example.online_shop.service.UserService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.ArrayList;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@WebMvcTest(controllers = SuperAdminController.class, excludeAutoConfiguration = {SecurityAutoConfiguration.class})
public class SuperAdminControllerWebLayerTest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    UserService userService;

    UserRequestDto userRequestDto;

    @BeforeEach
    void beforeEach() {
        userRequestDto = new UserRequestDto();
        userRequestDto.setUserId(0L);
        userRequestDto.setFirstName("Alex");
        userRequestDto.setLastName("Parker");
        userRequestDto.setEmail("test@test.com");
        userRequestDto.setPassword("test");
        userRequestDto.setBalance(0D);
    }

//    @Test
//    void testAddAdmin_whenValidUserDetailProvided_ReturnsCreatedUserDetails() throws Exception {
//        //Arrange
//        UserInfoResponseDto userResponseDto = new ModelMapper().map(userRequestDto, UserInfoResponseDto.class);
//        userResponseDto.setUserId(1L);
//        userResponseDto.setBalance(0D);
//        when(userService.createUser(any(UserRequestDto.class))).thenReturn(userResponseDto);
//
//        RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/api/superadmin/admin")
//                .contentType(MediaType.APPLICATION_JSON)
//                .accept(MediaType.APPLICATION_JSON)
//                .content(new ObjectMapper().writeValueAsString(userRequestDto));
//        //Act
//        MvcResult mvcResult = mockMvc.perform(requestBuilder).andReturn();
//        String responseBodyAsString = mvcResult.getResponse().getContentAsString();
//        UserResponseDto createdUser = new ObjectMapper().readValue(responseBodyAsString, UserResponseDto.class);
//
//        //Assert
//        Assertions.assertEquals(userRequestDto.getFirstName(), createdUser.getFirstName());
//        Assertions.assertEquals(userRequestDto.getLastName(), createdUser.getLastName());
//        Assertions.assertEquals(userRequestDto.getEmail(), createdUser.getEmail());
//        Assertions.assertNotNull(createdUser.getUserId());
//    }

    @Test
    void testAddAdmin_whenInValidUserDetailsProvided_return400StatusCode() throws Exception {
        //Arrange
        userRequestDto.setEmail("123456775");
        RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/api/superadmin/admin")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(userRequestDto));

        //Act
        MvcResult mvcResult = mockMvc.perform(requestBuilder).andReturn();

        //Assert
        Assertions.assertEquals(HttpStatus.BAD_REQUEST.value(), mvcResult.getResponse().getStatus());
    }

    @Test
    void testDeleteAdmin_whenUserIdProvided() throws Exception {
        //Arrange
        UserInfoResponseDto userResponseDto = new ModelMapper().map(userRequestDto, UserInfoResponseDto.class);
        userResponseDto.setUserId(1L);
        userResponseDto.setBalance(0D);
        when(userService.deleteUser(1L)).thenReturn(userResponseDto);

        RequestBuilder requestBuilder =
                MockMvcRequestBuilders.delete("/api/superadmin/admin/{id}", 1L);
        //Act
        MvcResult mvcResult = mockMvc.perform(requestBuilder).andReturn();
        String responseBodyAsString = mvcResult.getResponse().getContentAsString();
        UserInfoResponseDto createdUser = new ObjectMapper().readValue(responseBodyAsString, UserInfoResponseDto.class);
        //Assert
        Assertions.assertNotNull(createdUser.getUserId());
    }

    @Test
    void testAssignAdmin_whenUserIdProvided() throws Exception {
        //Arrange
        UserInfoResponseDto userResponseDto = new ModelMapper().map(userRequestDto, UserInfoResponseDto.class);
        userResponseDto.setUserId(1L);
        userResponseDto.setBalance(0D);
        userResponseDto.setRole(Role.ADMIN.name());
        when(userService.changeUserToAdmin(1L)).thenReturn(userResponseDto);

        RequestBuilder requestBuilder =
                MockMvcRequestBuilders.put("/api/superadmin/admin/{id}", 1L);
        //Act
        MvcResult mvcResult = mockMvc.perform(requestBuilder).andReturn();
        String responseBodyAsString = mvcResult.getResponse().getContentAsString();
        UserInfoResponseDto createdUser = new ObjectMapper().readValue(responseBodyAsString, UserInfoResponseDto.class);
        //Assert
        Assertions.assertEquals(Role.ADMIN.name(), createdUser.getRole());
    }
}