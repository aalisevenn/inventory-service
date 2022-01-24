package com.aliseven.inventoryservice.model.response;

import com.aliseven.inventoryservice.model.entity.ProductEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.math.BigDecimal;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class CreateOrderResponseVO {
    private Long id;
    private BigDecimal totalAmount;
    private Long userId;
    private Integer quantity;
    private List<ProductEntity> products;
}
