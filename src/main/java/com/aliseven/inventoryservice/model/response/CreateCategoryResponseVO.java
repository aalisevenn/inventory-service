package com.aliseven.inventoryservice.model.response;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;

@Data
@SuperBuilder
@NoArgsConstructor
public class CreateCategoryResponseVO {

    private Long id;
    private String name;
    private String description;
    private LocalDateTime updatedDate;
    private String updatedUser;
}
