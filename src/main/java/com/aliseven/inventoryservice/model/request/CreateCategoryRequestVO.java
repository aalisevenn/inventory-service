package com.aliseven.inventoryservice.model.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class CreateCategoryRequestVO {
    @NotNull
    private String name;
    private String description;
    private LocalDateTime updatedDate;
    private String updatedUser;
}
