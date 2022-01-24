package com.aliseven.inventoryservice.business;

import com.aliseven.inventoryservice.auth.UserDetailsImpl;
import com.aliseven.inventoryservice.business.iface.OrderService;
import com.aliseven.inventoryservice.business.iface.ProductService;
import com.aliseven.inventoryservice.business.iface.StockService;
import com.aliseven.inventoryservice.exception.BusinessException;
import com.aliseven.inventoryservice.exception.ExceptionConstants;
import com.aliseven.inventoryservice.model.entity.OrderEntity;
import com.aliseven.inventoryservice.model.entity.ProductEntity;
import com.aliseven.inventoryservice.model.entity.StockEntity;
import com.aliseven.inventoryservice.model.repository.OrderRepository;
import com.aliseven.inventoryservice.model.request.CreateOrderRequestVO;
import com.aliseven.inventoryservice.model.request.GetAllOrderDateSearchVO;
import com.aliseven.inventoryservice.model.response.CreateOrderResponseVO;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final ProductService productService;
    private final StockService stockService;

    public OrderServiceImpl(OrderRepository orderRepository, ProductService productService, StockService stockService) {
        this.orderRepository = orderRepository;
        this.productService = productService;
        this.stockService = stockService;
    }




    @Override
    @Transactional
    public CreateOrderResponseVO order(CreateOrderRequestVO request) {
        UserDetailsImpl user = (UserDetailsImpl)
                SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        final ProductEntity product = productService.findById(request.getBookId())
                .orElseThrow(() -> new BusinessException("Product Not Found!", ExceptionConstants.PRODUCT_NOT_FOUND));

        StockEntity stock = stockService.findByBook_Id(product.getId())
                .orElseThrow(() -> new BusinessException("Book Stock Not Found!", ExceptionConstants.STOCK_NOT_FOUND));

        if (request.getQuantity() > stock.getQuantity())
            throw new BusinessException("Book Stock Not Enough!", ExceptionConstants.BOOK_STOCK_NOT_ENOUGH);

        BigDecimal percentage = this.calculatePercentage(request.getDiscount(), product.getPrice());

        BigDecimal totalPrice = (product.getPrice()
                .subtract(percentage))
                .multiply(BigDecimal.valueOf(request.getQuantity()));

        stockService
                .updateStockQuantity(stock.getQuantity() - request.getQuantity(), stock.getId());

        OrderEntity order = orderRepository.save(OrderEntity.builder()
                .discount(request.getDiscount())
                .price(totalPrice)
                .products(Collections.singletonList(product))
                .quantity(request.getQuantity())
                .updateUser(user.getUsername())
                .userId(user.getId())
                .build());

        return CreateOrderResponseVO.builder()
                .id(order.getId())
                .products(order.getProducts())
                .userId(order.getUserId())
                .totalAmount(order.getPrice())
                .quantity(order.getQuantity())
                .build();
    }

    @Override
    public Optional<OrderEntity> findById(Long id) {
        return orderRepository.findById(id);
    }

    @Override
    public List<OrderEntity> findByUserIdAndActive(Long id, boolean isActive) {
        return orderRepository.findByUserIdAndActive(id, isActive);
    }

    @Override
    public List<OrderEntity> getAllBetweenDates(GetAllOrderDateSearchVO request) {
        return orderRepository.getAllBetweenDates(request.getStartDate(), request.getEndDate());
    }

    public BigDecimal calculatePercentage(BigDecimal obtained, BigDecimal total) {
        return obtained.multiply(BigDecimal.valueOf(100.0)).divide(total);
    }
}
