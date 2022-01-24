package com.aliseven.inventoryservice.controller;

import com.aliseven.inventoryservice.business.iface.OrderService;
import com.aliseven.inventoryservice.model.request.CreateOrderRequestVO;
import com.aliseven.inventoryservice.model.request.GetAllOrderDateSearchVO;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/order")
public class OrderController {
    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping("/save")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> save(@Valid @RequestBody CreateOrderRequestVO createOrderRequest) {
        return ResponseEntity.ok(orderService.order(createOrderRequest));
    }

    @PostMapping("/search")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> searchOrdersBetweenDates(@Valid @RequestBody GetAllOrderDateSearchVO request) {
        return ResponseEntity.ok(orderService.getAllBetweenDates(request));
    }

}
