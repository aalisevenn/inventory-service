package com.aliseven.inventoryservice.business;

import com.aliseven.inventoryservice.business.iface.StatisticsService;
import com.aliseven.inventoryservice.model.entity.OrderEntity;
import com.aliseven.inventoryservice.model.repository.OrderRepository;
import com.aliseven.inventoryservice.model.request.StatisticsRequestVO;
import com.aliseven.inventoryservice.model.response.StatisticsResponseVO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StatisticsServiceImpl implements StatisticsService {

    private final OrderRepository orderRepository;

    public StatisticsServiceImpl(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }


    @Override
    public List<OrderEntity> getStatisticMonthly(StatisticsRequestVO request) {
        return orderRepository.getByYearAndMonth(request.getYear(), request.getMonth());
    }

    @Override
    public List<OrderEntity> getAllOfCurrentMonth() {
        return orderRepository.getAllOfCurrentMonth();
    }

    @Override
    public List<StatisticsResponseVO> getAllOrderStatisticsPerMonth() {
        return orderRepository.getAllOrderStatisticsPerMonth();
    }
}
