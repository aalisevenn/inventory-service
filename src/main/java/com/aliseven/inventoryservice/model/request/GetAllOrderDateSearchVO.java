package com.aliseven.inventoryservice.model.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GetAllOrderDateSearchVO {
    private LocalDateTime startDate;
    private LocalDateTime endDate;
}
