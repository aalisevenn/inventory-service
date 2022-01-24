package com.aliseven.inventoryservice.model.repository;


import com.aliseven.inventoryservice.model.entity.StockEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface StockRepository extends JpaRepository<StockEntity, Long> {
    @Query("select s from StockEntity s where s.product.id = :bookId")
    Optional<StockEntity> findByBook_Id(Long bookId);

    @Modifying
    @Query("update StockEntity s set s.quantity= :quantity where s.id = :id")
    void updateStockQuantity(@Param("quantity") Integer quantity, @Param("id") Long id);
}
