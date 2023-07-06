package com.example.demo.controllers;

import com.example.demo.model.persistence.Cart;
import com.example.demo.model.persistence.Item;
import com.example.demo.model.persistence.User;
import com.example.demo.model.persistence.UserOrder;
import com.example.demo.model.persistence.repositories.OrderRepository;
import com.example.demo.model.persistence.repositories.UserRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Collections;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(MockitoJUnitRunner.class)
public class OrderControllerTest {
    @InjectMocks
    private OrderController orderController;

    @Mock
    private OrderRepository orderRepository;

    @Mock
    private UserRepository userRepository;

    private MockMvc mockMvc;

    @Before
    public void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(orderController).build();
    }

    @Test
    public void testSubmitSuccess() throws Exception {
        String userName = "dong";
        User user = new User();
        user.setId(1L);
        Cart cart = new Cart();
        cart.setId(1L);
        cart.setItems(Collections.singletonList(new Item()));
        user.setCart(cart);
        Mockito.when(userRepository.findByUsername(userName)).thenReturn(user);

        mockMvc.perform(post("/api/order/submit/{username}", userName))
                .andExpect(status().isOk());
    }

    @Test
    public void testSubmitInvalidUserName() throws Exception {
        mockMvc.perform(post("/api/order/submit/{username}", "dong"))
                .andExpect(status().isNotFound());
    }

    @Test
    public void testGetOrderForUserSuccess() throws Exception {
        String userName = "dong";
        User user = new User();
        user.setId(1L);
        user.setUsername(userName);
        UserOrder userOrder = new UserOrder();
        Mockito.when(userRepository.findByUsername(userName)).thenReturn(user);
        Mockito.when(orderRepository.findByUser(user)).thenReturn(Collections.singletonList(userOrder));

        mockMvc.perform(get("/api/order/history/{username}", userName))
                .andExpect(status().isOk());
    }

    @Test
    public void testGetOrderForUserFailed() throws Exception {
        mockMvc.perform(get("/api/order/history/{username}", "dong"))
                .andExpect(status().isNotFound());
    }
}
