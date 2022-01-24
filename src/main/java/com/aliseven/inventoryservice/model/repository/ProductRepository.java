package com.aliseven.inventoryservice.model.repository;

import com.aliseven.inventoryservice.model.entity.ProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<ProductEntity, Long> {
}
