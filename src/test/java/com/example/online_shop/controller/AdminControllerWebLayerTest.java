package com.example.online_shop.controller;

import com.example.online_shop.dto.requestDto.ProductRequestDto;
import com.example.online_shop.dto.requestDto.UserRequestDto;
import com.example.online_shop.dto.responseDto.UserInfoResponseDto;
import com.example.online_shop.entity.Product;
import com.example.online_shop.entity.Role;
import com.example.online_shop.service.ProductService;
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

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@WebMvcTest(controllers = AdminController.class, excludeAutoConfiguration = {SecurityAutoConfiguration.class})
public class AdminControllerWebLayerTest {

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    UserService userService;
    @MockBean
    ProductService productService;

    UserRequestDto userRequestDto;
    ProductRequestDto productRequestDto;

    @BeforeEach
    void beforeEach() {
        userRequestDto = new UserRequestDto();
        userRequestDto.setUserId(0L);
        userRequestDto.setFirstName("Alex");
        userRequestDto.setLastName("Parker");
        userRequestDto.setEmail("test@test.com");
        userRequestDto.setPassword("test");
        userRequestDto.setBalance(0D);

        productRequestDto = new ProductRequestDto();
        productRequestDto.setName("Cheese");
        productRequestDto.setPrice(30D);
    }

    @Test
    void testDeleteUser_whenUserIdProvided() throws Exception {
        //Arrange
        UserInfoResponseDto userResponseDto = new ModelMapper().map(userRequestDto, UserInfoResponseDto.class);
        userResponseDto.setUserId(1L);
        userResponseDto.setBalance(0D);
        when(userService.deleteUser(1L)).thenReturn(userResponseDto);

        RequestBuilder requestBuilder =
                MockMvcRequestBuilders.delete("/api/admin/user/{id}", 1L);
        //Act
        MvcResult mvcResult = mockMvc.perform(requestBuilder).andReturn();
        String responseBodyAsString = mvcResult.getResponse().getContentAsString();
        UserInfoResponseDto createdUser = new ObjectMapper().readValue(responseBodyAsString, UserInfoResponseDto.class);
        //Assert
        Assertions.assertEquals(1L, createdUser.getUserId());
    }

    @Test
    void testAddProduct_whenValidProductInfoProvided() throws Exception {
        //Arrange
        Product product = new ModelMapper().map(productRequestDto, Product.class);
        product.setProductId(1L);
        when(productService.create(any(ProductRequestDto.class))).thenReturn(product);

        RequestBuilder requestBuilder =
                MockMvcRequestBuilders.post("/api/admin/product")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(productRequestDto));
        //Act
        MvcResult mvcResult = mockMvc.perform(requestBuilder).andReturn();
        String responseBodyAsString = mvcResult.getResponse().getContentAsString();
        Product createdProduct = new ObjectMapper().readValue(responseBodyAsString, Product.class);

        //Assert
        Assertions.assertEquals(product.getProductId(), createdProduct.getProductId());
        Assertions.assertEquals(product.getName(), createdProduct.getName());
        Assertions.assertEquals(product.getPrice(), createdProduct.getPrice());
    }

    @Test
    void testAddProduct_whenInValidProductInfoProvided() throws Exception {
        //Arrange
        productRequestDto.setPrice(0D);
        RequestBuilder requestBuilder =
                MockMvcRequestBuilders.post("/api/admin/product")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(productRequestDto));

        //Act
        MvcResult mvcResult = mockMvc.perform(requestBuilder).andReturn();

        //Assert
        Assertions.assertEquals(HttpStatus.BAD_REQUEST.value(), mvcResult.getResponse().getStatus());
    }

    @Test
    void testDeleteProduct_whenProductIdIsProvided() throws Exception {
        //Arrange
        Product product = new ModelMapper().map(productRequestDto, Product.class);
        product.setProductId(1L);
        when(productService.delete(product.getProductId())).thenReturn(product);

        RequestBuilder requestBuilder =
                MockMvcRequestBuilders.delete("/api/admin/product/{id}", product.getProductId());
        //Act
        MvcResult mvcResult = mockMvc.perform(requestBuilder).andReturn();
        String responseBodyAsString = mvcResult.getResponse().getContentAsString();
        Product deletedProduct = new ObjectMapper().readValue(responseBodyAsString, Product.class);
        //Assert
        Assertions.assertEquals(product.getProductId(), deletedProduct.getProductId());
    }

    @Test
    void testEditProduct_whenValidProductInfoProvided() throws Exception {
        //Arrange
        Product product = new ModelMapper().map(productRequestDto, Product.class);
        product.setProductId(1L);
        when(productService.editProduct(any(Product.class))).thenReturn(product);

        RequestBuilder requestBuilder =
                MockMvcRequestBuilders.put("/api/admin/product")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(product));
        //Act
        MvcResult mvcResult = mockMvc.perform(requestBuilder).andReturn();
        String responseBodyAsString = mvcResult.getResponse().getContentAsString();
        Product editedProduct = new ObjectMapper().readValue(responseBodyAsString, Product.class);
        //Assert
        Assertions.assertEquals(product.getName(), editedProduct.getName());
        Assertions.assertEquals(product.getPrice(), editedProduct.getPrice());
    }

    @Test
    void testEditProduct_whenInValidProductInfoProvided() throws Exception {
        //Arrange
        productRequestDto.setName("");
        RequestBuilder requestBuilder =
                MockMvcRequestBuilders.put("/api/admin/product")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(productRequestDto));

        //Act
        MvcResult mvcResult = mockMvc.perform(requestBuilder).andReturn();

        //Assert
        Assertions.assertEquals(HttpStatus.BAD_REQUEST.value(), mvcResult.getResponse().getStatus());
    }
}
