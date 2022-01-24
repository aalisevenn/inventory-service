package com.aliseven.inventoryservice.service;


import com.aliseven.inventoryservice.business.StatisticsServiceImpl;
import com.aliseven.inventoryservice.business.iface.StatisticsService;
import com.aliseven.inventoryservice.model.entity.OrderEntity;
import com.aliseven.inventoryservice.model.repository.OrderRepository;
import com.aliseven.inventoryservice.model.request.StatisticsRequestVO;
import com.aliseven.inventoryservice.model.response.StatisticsResponseVO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class StatisticsServiceImplTest {

    private OrderRepository orderRepository;
    private StatisticsService statisticsService;

    @BeforeEach
    public void setUp() {
        orderRepository = mock(OrderRepository.class);
        statisticsService = new StatisticsServiceImpl(orderRepository);
    }

    @Test
    void testStatisticMonthly() {
        StatisticsRequestVO request = new StatisticsRequestVO(2021,5);
        OrderEntity order = OrderEntity.builder().id(1L).build();
        when(orderRepository.getByYearAndMonth(anyInt(),anyInt())).thenReturn(Collections.singletonList(order));
        final List<OrderEntity> result = statisticsService.getStatisticMonthly(request);
        assertEquals(result.get(0).getId(),order.getId());

    }

    @Test
    void testAllOfCurrentMonth() {
        OrderEntity order = OrderEntity.builder().id(1L).build();
        when(orderRepository.getAllOfCurrentMonth()).thenReturn(Collections.singletonList(order));
        final List<OrderEntity> result = statisticsService.getAllOfCurrentMonth();
        assertEquals(result.get(0).getId(),order.getId());
    }

    @Test
    void testAllOrderStatisticsPerMonth() {
        when(orderRepository.getAllOrderStatisticsPerMonth()).thenReturn(Collections.EMPTY_LIST);
        List<StatisticsResponseVO> result = statisticsService.getAllOrderStatisticsPerMonth();
        assertEquals(result.size(),0);
    }
}