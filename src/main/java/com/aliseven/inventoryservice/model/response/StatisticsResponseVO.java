package com.aliseven.inventoryservice.model.response;

import java.math.BigDecimal;

public interface StatisticsResponseVO {
    Integer getYear();

    Integer getMonth();

    Integer getOrderCount();

    Integer getBookCount();

    BigDecimal getTotalAmount();
}
