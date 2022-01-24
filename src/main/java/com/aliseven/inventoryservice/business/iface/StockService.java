package com.aliseven.inventoryservice.business.iface;



import com.aliseven.inventoryservice.model.entity.StockEntity;
import com.aliseven.inventoryservice.model.request.CreateStockRequestVO;
import com.aliseven.inventoryservice.model.request.UpdateStockRequestVO;
import com.aliseven.inventoryservice.model.response.CreateStockResponseVO;
import com.aliseven.inventoryservice.model.response.UpdateStockResponseVO;

import java.util.Optional;

public interface StockService {
    CreateStockResponseVO save(CreateStockRequestVO request);

    Optional<StockEntity> findByBook_Id(Long bookId);

    void updateStockQuantity(Integer quantity, Long id);

    UpdateStockResponseVO update(UpdateStockRequestVO request);
}
