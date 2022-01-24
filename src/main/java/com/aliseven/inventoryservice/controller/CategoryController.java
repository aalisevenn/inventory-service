package com.aliseven.inventoryservice.controller;


import com.aliseven.inventoryservice.business.iface.CategoryService;
import com.aliseven.inventoryservice.model.request.CreateCategoryRequestVO;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/category")
public class CategoryController {
    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }


    @PostMapping("/save")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<?> save(@Valid @RequestBody CreateCategoryRequestVO createCategoryRequest) {
        return ResponseEntity.ok(categoryService.save(createCategoryRequest));
    }
}
