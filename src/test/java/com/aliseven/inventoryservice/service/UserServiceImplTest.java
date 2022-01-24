package com.aliseven.inventoryservice.service;


import com.aliseven.inventoryservice.auth.JwtUtil;
import com.aliseven.inventoryservice.business.UserServiceImpl;
import com.aliseven.inventoryservice.business.iface.OrderService;
import com.aliseven.inventoryservice.business.iface.UserService;
import com.aliseven.inventoryservice.model.entity.RoleEntity;
import com.aliseven.inventoryservice.model.repository.RoleRepository;
import com.aliseven.inventoryservice.model.repository.UserRepository;
import com.aliseven.inventoryservice.model.request.CreateUserRequestVO;
import com.aliseven.inventoryservice.model.response.CreateUserResponseVO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.crypto.password.PasswordEncoder;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Collections;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class UserServiceImplTest {

    private UserRepository userRepository;
    private RoleRepository roleRepository;
    private PasswordEncoder encoder;
    private AuthenticationManager authenticationManager;
    private JwtUtil jwtUtil;
    private OrderService orderService;
    @MockBean
    private Authentication authentication;
    @MockBean
    private SecurityContext securityContext;
    private UserService userService;

    @BeforeEach
    public void setUp() {
        userRepository = mock(UserRepository.class);
        roleRepository = mock(RoleRepository.class);
        encoder = mock(PasswordEncoder.class);
        jwtUtil = mock(JwtUtil.class);
        orderService = mock(OrderService.class);
        authenticationManager = mock(AuthenticationManager.class);
        authentication = mock(Authentication.class);
        securityContext = mock(SecurityContext.class);
        userService = new UserServiceImpl(userRepository,roleRepository,encoder,
                authenticationManager,jwtUtil,orderService);
    }

    @Test
    void testRegisterUser() {
        CreateUserRequestVO request =
                new CreateUserRequestVO("ali","12345","test@hotmail.com","ADMIN", Collections.EMPTY_SET);

        when(roleRepository.findByName(any())).thenReturn(Optional.of(new RoleEntity()));
        CreateUserResponseVO message = userService.registerUser(request);
        assertEquals(message.getMessage(),"User registered successfully");


    }

    @Test
    void testAuthenticateUser() {
    }

    @Test
    void testCustomerOrders() {
    }
}