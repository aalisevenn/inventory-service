package com.aliseven.inventoryservice.model.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class CreateProductRequestVO {

    private String name;
    private BigDecimal price;
    @NotNull
    private Long categoryId;
    private LocalDateTime updatedDate;
    private String updatedUser;
}
