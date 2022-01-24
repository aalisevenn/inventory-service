package com.aliseven.inventoryservice.model.response;

import com.aliseven.inventoryservice.model.entity.ProductEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public class UpdateStockResponseVO {
    private Long id;
    private String name;
    private Integer quantity;
    private ProductEntity book;
}
