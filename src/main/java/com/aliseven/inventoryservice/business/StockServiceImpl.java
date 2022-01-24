package com.aliseven.inventoryservice.business;

import com.aliseven.inventoryservice.business.iface.ProductService;
import com.aliseven.inventoryservice.business.iface.StockService;
import com.aliseven.inventoryservice.exception.BusinessException;
import com.aliseven.inventoryservice.exception.ExceptionConstants;
import com.aliseven.inventoryservice.model.entity.ProductEntity;
import com.aliseven.inventoryservice.model.entity.StockEntity;
import com.aliseven.inventoryservice.model.repository.StockRepository;
import com.aliseven.inventoryservice.model.request.CreateStockRequestVO;
import com.aliseven.inventoryservice.model.request.UpdateStockRequestVO;
import com.aliseven.inventoryservice.model.response.CreateStockResponseVO;
import com.aliseven.inventoryservice.model.response.UpdateStockResponseVO;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

@Service
public class StockServiceImpl implements StockService {


    private final StockRepository stockRepository;
    private final ProductService productService;

    public StockServiceImpl(StockRepository stockRepository, ProductService productService) {
        this.stockRepository = stockRepository;
        this.productService = productService;
    }




    @Transactional
    @Override
    public CreateStockResponseVO save(CreateStockRequestVO request) {
        ProductEntity product = productService.findById(request.getBookId())
                .orElseThrow(() -> new BusinessException("Product Not found", ExceptionConstants.PRODUCT_NOT_FOUND));

        final String user = SecurityContextHolder.getContext().getAuthentication().getName();
        final StockEntity bookInDb = stockRepository.save(
                StockEntity.builder()
                        .name(request.getName())
                        .product(product).updateUser(user)
                        .quantity(request.getQuantity())
                        .build());

        return CreateStockResponseVO.builder()
                .product(bookInDb.getProduct())
                .id(product.getId())
                .name(bookInDb.getName())
                .quantity(bookInDb.getQuantity())
                .updatedUser(bookInDb.getUpdateUser())
                .build();
    }

    @Override
    public Optional<StockEntity> findByBook_Id(Long bookId) {
        return stockRepository.findByBook_Id(bookId);
    }

    @Override
    public void updateStockQuantity(Integer quantity, Long id) {
        stockRepository.updateStockQuantity(quantity, id);
    }

    @Transactional
    @Override
    public UpdateStockResponseVO update(UpdateStockRequestVO request) {
        final StockEntity stock = stockRepository.findByBook_Id(request.getBookId())
                .orElseThrow(() -> new BusinessException("Product Not Found", ExceptionConstants.PRODUCT_NOT_FOUND));
        stock.setQuantity(request.getQuantity());
        stockRepository.save(stock);
        return UpdateStockResponseVO.builder()
                .book(stock.getProduct())
                .id(stock.getId())
                .quantity(stock.getQuantity())
                .name(stock.getName())
                .build();
    }
}

