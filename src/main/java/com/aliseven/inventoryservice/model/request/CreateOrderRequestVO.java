package com.aliseven.inventoryservice.model.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class CreateOrderRequestVO {
    @NotNull
    private Long bookId;
    private BigDecimal discount;
    private BigDecimal amount;
    @NotNull
    private int quantity;
}
