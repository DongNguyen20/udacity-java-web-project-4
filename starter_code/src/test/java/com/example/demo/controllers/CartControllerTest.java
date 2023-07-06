package com.example.demo.controllers;

import com.example.demo.model.persistence.Cart;
import com.example.demo.model.persistence.Item;
import com.example.demo.model.persistence.User;
import com.example.demo.model.persistence.repositories.CartRepository;
import com.example.demo.model.persistence.repositories.ItemRepository;
import com.example.demo.model.persistence.repositories.UserRepository;
import com.example.demo.model.requests.ModifyCartRequest;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;

public class CartControllerTest {
    @InjectMocks
    private CartController cartController;

    @Mock
    private UserRepository userRepository;

    @Mock
    private ItemRepository itemRepository;

    @Mock
    private CartRepository cartRepository;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testAddToCartSuccess() {
        ModifyCartRequest request = new ModifyCartRequest();
        request.setUsername("dong");
        request.setItemId(1L);
        request.setQuantity(1);

        User user = new User();
        Item item = new Item();
        item.setId(1L);
        item.setName("Harry Potter 3");
        item.setDescription("HP and the goblet of fire");
        item.setPrice(BigDecimal.valueOf(30.0));
        Cart cart = new Cart();
        cart.setId(1L);
        cart.addItem(item);
        user.setCart(cart);

        Mockito.when(userRepository.findByUsername(request.getUsername())).thenReturn(user);
        Mockito.when(itemRepository.findById(request.getItemId())).thenReturn(java.util.Optional.of(item));

        ResponseEntity<Cart> response = cartController.addTocart(request);
        Assert.assertNotNull(response);
        Assert.assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    public void testAddToCartInvalidUsername() {
        ModifyCartRequest request = new ModifyCartRequest();
        request.setUsername("dong");
        request.setItemId(1L);
        request.setQuantity(1);

        ResponseEntity<Cart> response = cartController.addTocart(request);
        Assert.assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    public void testAddToCartInvalidItemId() {
        ModifyCartRequest request = new ModifyCartRequest();
        request.setUsername("dong");
        request.setItemId(1L);
        request.setQuantity(1);
        User user = new User();
        Cart cart = new Cart();
        cart.setId(1L);
        user.setCart(cart);
        Mockito.when(userRepository.findByUsername(request.getUsername())).thenReturn(user);

        ResponseEntity<Cart> response = cartController.addTocart(request);
        Assert.assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    public void testRemoveFromCartSuccess() {
        ModifyCartRequest request = new ModifyCartRequest();
        request.setUsername("dong");
        request.setItemId(1L);
        request.setQuantity(1);

        User user = new User();
        Item item = new Item();
        item.setId(1L);
        item.setName("Harry Potter 3");
        item.setDescription("HP and the goblet of fire");
        item.setPrice(BigDecimal.valueOf(30.0));
        Cart cart = new Cart();
        cart.setId(1L);
        cart.addItem(item);
        user.setCart(cart);

        Mockito.when(userRepository.findByUsername(request.getUsername())).thenReturn(user);
        Mockito.when(itemRepository.findById(request.getItemId())).thenReturn(java.util.Optional.of(item));

        ResponseEntity<Cart> response = cartController.removeFromcart(request);
        Assert.assertNotNull(response);
        Assert.assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    public void testRemoveFromCartInvalidUsername() {
        ModifyCartRequest request = new ModifyCartRequest();
        request.setUsername("dong");
        request.setItemId(1L);
        request.setQuantity(1);

        ResponseEntity<Cart> response = cartController.removeFromcart(request);
        Assert.assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    public void testRemoveFromCartInvalidItemId() {
        ModifyCartRequest request = new ModifyCartRequest();
        request.setUsername("dong");
        request.setItemId(1L);
        request.setQuantity(1);
        User user = new User();
        Cart cart = new Cart();
        cart.setId(1L);
        user.setCart(cart);
        Mockito.when(userRepository.findByUsername(request.getUsername())).thenReturn(user);

        ResponseEntity<Cart> response = cartController.removeFromcart(request);
        Assert.assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }
}
