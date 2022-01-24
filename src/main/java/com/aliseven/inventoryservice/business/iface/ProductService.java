package com.aliseven.inventoryservice.business.iface;



import com.aliseven.inventoryservice.model.entity.ProductEntity;
import com.aliseven.inventoryservice.model.request.CreateProductRequestVO;
import com.aliseven.inventoryservice.model.response.CreateProductResponseVO;

import java.util.Optional;

public interface ProductService {
    CreateProductResponseVO save(CreateProductRequestVO request);

    Optional<ProductEntity> findById(Long id);
}
