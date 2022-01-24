package com.aliseven.inventoryservice.business.iface;



import com.aliseven.inventoryservice.model.entity.CategoryEntity;
import com.aliseven.inventoryservice.model.request.CreateCategoryRequestVO;
import com.aliseven.inventoryservice.model.response.CreateCategoryResponseVO;

import java.util.Optional;

public interface CategoryService {
    CreateCategoryResponseVO save(CreateCategoryRequestVO request);

    Optional<CategoryEntity> findById(Long id);
}
