package com.aliseven.inventoryservice.model.repository;


import com.aliseven.inventoryservice.model.entity.OrderEntity;
import com.aliseven.inventoryservice.model.response.StatisticsResponseVO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface OrderRepository extends JpaRepository<OrderEntity, Long> {
    List<OrderEntity> findByUserIdAndActive(Long id, boolean isActive);

    @Query("select e from OrderEntity e where year(e.createdDate) = ?1 and month(e.createdDate) = ?2")
    List<OrderEntity> getByYearAndMonth(int year, int month);

    @Query("select e from OrderEntity e where year(e.createdDate) = year(current_date) and  month(e.createdDate) = month(current_date)")
    List<OrderEntity> getAllOfCurrentMonth();

    @Query("select year(o.createdDate) AS year ,month(o.createdDate) AS month, count(o.id) AS orderCount, sum(o.quantity) AS bookCount, sum(o.price) AS totalAmount" +
            " from OrderEntity o group by year(o.createdDate),month(o.createdDate)" +
            " order by year(o.createdDate),month(o.createdDate)")
    List<StatisticsResponseVO> getAllOrderStatisticsPerMonth();

    @Query("select o from OrderEntity o where o.createdDate BETWEEN :startDate AND :endDate")
    List<OrderEntity> getAllBetweenDates(@Param("startDate") LocalDateTime startDate, @Param("endDate") LocalDateTime endDate);
}
