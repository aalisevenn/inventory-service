package com.aliseven.inventoryservice.business.iface;



import com.aliseven.inventoryservice.model.entity.OrderEntity;
import com.aliseven.inventoryservice.model.request.StatisticsRequestVO;
import com.aliseven.inventoryservice.model.response.StatisticsResponseVO;

import java.util.List;

public interface StatisticsService {
    List<OrderEntity> getStatisticMonthly(StatisticsRequestVO request);

    List<OrderEntity> getAllOfCurrentMonth();

    List<StatisticsResponseVO> getAllOrderStatisticsPerMonth();
}
