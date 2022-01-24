package com.aliseven.inventoryservice.service;

import com.aliseven.inventoryservice.business.StockServiceImpl;
import com.aliseven.inventoryservice.business.iface.ProductService;
import com.aliseven.inventoryservice.business.iface.StockService;
import com.aliseven.inventoryservice.model.entity.ProductEntity;
import com.aliseven.inventoryservice.model.entity.StockEntity;
import com.aliseven.inventoryservice.model.repository.StockRepository;
import com.aliseven.inventoryservice.model.request.CreateStockRequestVO;
import com.aliseven.inventoryservice.model.request.UpdateStockRequestVO;
import com.aliseven.inventoryservice.model.response.CreateStockResponseVO;
import com.aliseven.inventoryservice.model.response.UpdateStockResponseVO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

class StockServiceImplTest {

    private StockRepository stockRepository;
    private ProductService bookService;
    private StockService stockService;
    @MockBean
    private Authentication authentication;
    @MockBean
    private SecurityContext securityContext;

    @BeforeEach
    public void setUp() {
        stockRepository = mock(StockRepository.class);
        bookService = mock(ProductService.class);
        authentication = mock(Authentication.class);
        securityContext = mock(SecurityContext.class);
        stockService = new StockServiceImpl(stockRepository, bookService);
        when(securityContext.getAuthentication()).thenReturn(authentication);
        SecurityContextHolder.setContext(securityContext);
        when(SecurityContextHolder.getContext().getAuthentication().getName()).thenReturn("ali");

    }

    @Test
    public void testSave() {
        CreateStockRequestVO request = CreateStockRequestVO.builder()
                .quantity(25).bookId(1L).name("testName").build();
        ProductEntity book = ProductEntity.builder().price(BigDecimal.TEN).id(1L).name("testName").build();
        when(bookService.findById(anyLong())).thenReturn(Optional.of(book));
        StockEntity stock = StockEntity.builder().id(1L).quantity(25).product(book).build();
        when(stockRepository.save(any())).thenReturn(stock);
        CreateStockResponseVO response = stockService.save(request);
        assertEquals(response.getProduct().getId(),request.getBookId());
    }

    @Test
    public void testFindByBook_Id() {
        StockEntity stock = StockEntity.builder().id(1L).quantity(25).product(new ProductEntity()).build();

        when(stockRepository.findByBook_Id(anyLong())).thenReturn(Optional.of(stock));

        StockEntity result = stockService.findByBook_Id(anyLong()).orElse(null);

        assertEquals(result.getId(), stock.getId());
    }

    @Test
    void testUpdateStockQuantity() {
        doNothing().when(stockRepository).updateStockQuantity(anyInt(),anyLong());
        stockService.updateStockQuantity(anyInt(),anyLong());
    }

    @Test
    void testUpdate() {
        UpdateStockRequestVO request = new UpdateStockRequestVO(1L,5);
        StockEntity stock = StockEntity.builder().id(1L).quantity(25).product(new ProductEntity()).build();
        when(stockRepository.findByBook_Id(anyLong())).thenReturn(Optional.of(stock));
        UpdateStockResponseVO response = stockService.update(request);
        assertEquals(response.getId(),stock.getId());
    }
}