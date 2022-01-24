package com.aliseven.inventoryservice.model.response;

import com.aliseven.inventoryservice.model.entity.ProductEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class CreateStockResponseVO {

    private Long id;
    private String name;
    private ProductEntity product;
    private int quantity;
    private String updatedUser;
}
