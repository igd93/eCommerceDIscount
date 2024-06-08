package com.example.grocery.controllers;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.example.grocery.dto.CartItemDTO;
import com.example.grocery.services.CartItemService;

@ExtendWith(MockitoExtension.class)
class CartItemControllerTest {

    @Mock
    private CartItemService cartItemService;

    @InjectMocks
    private CartItemController cartItemController;

    private CartItemDTO cartItem1;
    private CartItemDTO cartItem2;

    @BeforeEach
    void setUp() {
        cartItem1 = new CartItemDTO();
        cartItem1.setPrice(BigDecimal.valueOf(10.00));

        cartItem2 = new CartItemDTO();
        cartItem2.setPrice(BigDecimal.valueOf(20.00));
    }

    @Test
    void testGetCartItems() {
        List<CartItemDTO> cartItems = Arrays.asList(cartItem1, cartItem2);
        when(cartItemService.findAll()).thenReturn(cartItems);

        ResponseEntity<?> response = cartItemController.getCartItems();
        Map<String, Object> body = (Map<String, Object>) response.getBody();

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(body).isNotNull();
        assertThat(body.get("cartItems")).isEqualTo(cartItems);
        assertThat(body.get("totalAmount")).isEqualTo(BigDecimal.valueOf(30.00));
    }

    @Test
    void testGetCartItemsEmpty() {
        when(cartItemService.findAll()).thenReturn(Arrays.asList());

        ResponseEntity<?> response = cartItemController.getCartItems();
        String body = (String) response.getBody();

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
        assertThat(body).isEqualTo("Shopping cart is empty");
    }

    @Test
    void testAddCartItem() {
        when(cartItemService.addCartItem(1L, 2)).thenReturn(cartItem1);

        ResponseEntity<CartItemDTO> response = cartItemController.addCartItem(1L, 2);
        CartItemDTO body = response.getBody();

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(body).isEqualTo(cartItem1);
    }

    @Test
    void testUpdateCartItem() {
        when(cartItemService.updateCartItem(1L, 3)).thenReturn(cartItem2);

        ResponseEntity<CartItemDTO> response = cartItemController.updateCartItem(1L, 3);
        CartItemDTO body = response.getBody();

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(body).isEqualTo(cartItem2);
    }

    @Test
    void testRemoveCartItem() {
        doNothing().when(cartItemService).removeCartItem(1L);

        ResponseEntity<Void> response = cartItemController.removeCartItem(1L);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
        verify(cartItemService, times(1)).removeCartItem(1L);
    }
}
