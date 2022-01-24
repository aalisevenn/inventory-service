package com.aliseven.inventoryservice.business.iface;

import com.aliseven.inventoryservice.model.entity.OrderEntity;
import com.aliseven.inventoryservice.model.request.CreateOrderRequestVO;
import com.aliseven.inventoryservice.model.request.GetAllOrderDateSearchVO;
import com.aliseven.inventoryservice.model.response.CreateOrderResponseVO;

import java.util.List;
import java.util.Optional;

public interface OrderService {
    CreateOrderResponseVO order(CreateOrderRequestVO request);

    Optional<OrderEntity> findById(Long id);

    List<OrderEntity> findByUserIdAndActive(Long id, boolean isActive);

    List<OrderEntity> getAllBetweenDates(GetAllOrderDateSearchVO request);
}
