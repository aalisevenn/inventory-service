package com.aliseven.inventoryservice.controller;



import com.aliseven.inventoryservice.business.iface.UserService;
import com.aliseven.inventoryservice.model.request.CreateUserRequestVO;
import com.aliseven.inventoryservice.model.request.GetCustomerOrderRequestVO;
import com.aliseven.inventoryservice.model.request.LoginRequestVO;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/user")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/signin")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequestVO loginRequest) {
        return ResponseEntity.ok(userService.authenticateUser(loginRequest));
    }

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@Valid @RequestBody CreateUserRequestVO createUserRequest) {
        return ResponseEntity.ok(userService.registerUser(createUserRequest));
    }

    @PostMapping("/orders")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<?> getCustomerOrders(@RequestBody GetCustomerOrderRequestVO request) {
        return ResponseEntity.ok(userService.getCustomerOrders(request));
    }
}