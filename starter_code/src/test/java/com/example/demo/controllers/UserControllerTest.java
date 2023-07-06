package com.example.demo.controllers;

import com.example.demo.model.persistence.Cart;
import com.example.demo.model.persistence.User;
import com.example.demo.model.persistence.repositories.CartRepository;
import com.example.demo.model.persistence.repositories.UserRepository;
import com.example.demo.model.requests.CreateUserRequest;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Objects;

public class UserControllerTest {
    @InjectMocks
    private UserController userController;

    @Mock
    private UserRepository userRepository;

    @Mock
    private CartRepository cartRepository;

    @Mock
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    public User createUser() {
        User user = new User();
        user.setId(1L);
        user.setUsername("dong");
        user.setPassword("123456a");
        user.setCart(new Cart());
        return user;
    }

    @Test
    public void testFindByIdSuccess() {
        User user = createUser();
        Long id = 1L;
        Mockito.when(userRepository.findById(id)).thenReturn(java.util.Optional.of(user));
        ResponseEntity<User> response = userController.findById(id);

        Assert.assertEquals(HttpStatus.OK, response.getStatusCode());
        Assert.assertEquals(user, response.getBody());
    }

    @Test
    public void testFindByUserNameSuccess() {
        User user = createUser();
        String userName = "dong";
        Mockito.when(userRepository.findByUsername(userName)).thenReturn(user);
        ResponseEntity<User> response = userController.findByUserName(userName);
        Assert.assertNotNull(response);
        Assert.assertEquals(HttpStatus.OK, response.getStatusCode());
        Assert.assertEquals(user.getId(), Objects.requireNonNull(response.getBody()).getId());
    }

    @Test
    public void testFindByUserNameFailed() {
        ResponseEntity<User> response = userController.findByUserName("Udacity");
        Assert.assertNotNull(response);
        Assert.assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    public void testCreateUserSuccess() {
        CreateUserRequest request = new CreateUserRequest();
        String password = "HopeNo1";
        request.setUsername("dong2");
        request.setPassword(password);
        request.setConfirmPassword(password);
        Mockito.when(bCryptPasswordEncoder.encode(password)).thenReturn("encrypted password successfully");

        ResponseEntity<User> response = userController.createUser(request);
        Assert.assertNotNull(response);
        Assert.assertEquals(HttpStatus.OK, response.getStatusCode());

        User user = response.getBody();
        Assert.assertNotNull(user);
        Assert.assertEquals(request.getUsername(), user.getUsername());
    }

    @Test
    public void testCreateUserInvalidPasswordLength() {
        CreateUserRequest request = new CreateUserRequest();
        String password = "HopeNo";
        request.setUsername("dong2");
        request.setPassword(password);
        request.setConfirmPassword(password);
        ResponseEntity<User> response = userController.createUser(request);
        Assert.assertNotNull(response);
        Assert.assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    public void testCreateUserInvalidConfirmPassword() {
        CreateUserRequest request = new CreateUserRequest();
        String password = "HopeNo1";
        request.setUsername("dong2");
        request.setPassword(password);
        request.setConfirmPassword("Thursday");
        ResponseEntity<User> response = userController.createUser(request);
        Assert.assertNotNull(response);
        Assert.assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }
}
