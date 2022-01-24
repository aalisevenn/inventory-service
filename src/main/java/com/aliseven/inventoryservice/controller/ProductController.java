package com.aliseven.inventoryservice.controller;

import com.aliseven.inventoryservice.business.iface.ProductService;
import com.aliseven.inventoryservice.model.request.CreateProductRequestVO;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/book")
public class ProductController {
    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @PostMapping("/save")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<?> save(@Valid @RequestBody CreateProductRequestVO createBookRequest) {
        return ResponseEntity.ok(productService.save(createBookRequest));
    }
}
