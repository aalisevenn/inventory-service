package com.aliseven.inventoryservice.controller;


import com.aliseven.inventoryservice.business.iface.StockService;
import com.aliseven.inventoryservice.model.request.CreateStockRequestVO;
import com.aliseven.inventoryservice.model.request.UpdateStockRequestVO;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/stock")
public class StockController {

    private final StockService stockService;

    public StockController(StockService stockService) {
        this.stockService = stockService;
    }

    @PostMapping("/save")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> save(@Valid @RequestBody CreateStockRequestVO createStockRequest) {
        return ResponseEntity.ok(stockService.save(createStockRequest));
    }

    @PostMapping("/update")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> update(@Valid @RequestBody UpdateStockRequestVO request) {
        return ResponseEntity.ok(stockService.update(request));
    }
}
