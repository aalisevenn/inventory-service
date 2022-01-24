package com.aliseven.inventoryservice.service;


import com.aliseven.inventoryservice.auth.UserDetailsImpl;
import com.aliseven.inventoryservice.business.OrderServiceImpl;
import com.aliseven.inventoryservice.business.iface.OrderService;
import com.aliseven.inventoryservice.business.iface.ProductService;
import com.aliseven.inventoryservice.business.iface.StockService;
import com.aliseven.inventoryservice.model.entity.CategoryEntity;
import com.aliseven.inventoryservice.model.entity.OrderEntity;
import com.aliseven.inventoryservice.model.entity.ProductEntity;
import com.aliseven.inventoryservice.model.entity.StockEntity;
import com.aliseven.inventoryservice.model.repository.OrderRepository;
import com.aliseven.inventoryservice.model.request.CreateOrderRequestVO;
import com.aliseven.inventoryservice.model.response.CreateOrderResponseVO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

class OrderServiceImplTest {

    private OrderRepository orderRepository;
    private ProductService bookService;
    private StockService stockService;
    private OrderService orderService;
    @MockBean
    private Authentication authentication;
    @MockBean
    private SecurityContext securityContext;

    @BeforeEach
    public void setUp() {
        orderRepository = mock(OrderRepository.class);
        bookService = mock(ProductService.class);
        stockService = mock(StockService.class);
        authentication = mock(Authentication.class);
        securityContext = mock(SecurityContext.class);
        orderService = new OrderServiceImpl(orderRepository, bookService, stockService);
        UserDetailsImpl userDetails =
                new UserDetailsImpl(1L,"ali","ali@gmail.com","12345", Collections.EMPTY_LIST);
        when(securityContext.getAuthentication()).thenReturn(authentication);
        SecurityContextHolder.setContext(securityContext);
        when(SecurityContextHolder.getContext().getAuthentication().getPrincipal()).thenReturn(userDetails);

    }

    @Test
    public void order() {
        CreateOrderRequestVO request = CreateOrderRequestVO.builder()
                .amount(BigDecimal.TEN).bookId(1L).discount(BigDecimal.ONE).quantity(2).build();

        ProductEntity book = ProductEntity.builder().name("Gone Girl").id(1L)
                .category(new CategoryEntity()).price(BigDecimal.TEN).build();

        when(bookService.findById(anyLong())).thenReturn(Optional.of(book));

        StockEntity stock = StockEntity.builder().product(book).id(1L).quantity(25).build();

        when(stockService.findByBook_Id(anyLong())).thenReturn(Optional.of(stock));

        doNothing().when(stockService).updateStockQuantity(anyInt(),anyLong());

        OrderEntity order = OrderEntity.builder().id(1L)
                .products(Collections.singletonList(book)).price(BigDecimal.TEN).quantity(20).build();

        when(orderRepository.save(any())).thenReturn(order);

        CreateOrderResponseVO response = orderService.order(request);

        assertEquals(response.getProducts().get(0).getId(), request.getBookId());


    }

    @Test
    void findById() {
        OrderEntity order = OrderEntity.builder().id(1L)
                .products(Collections.EMPTY_LIST).price(BigDecimal.TEN).quantity(20).build();

        when(orderRepository.findById(anyLong())).thenReturn(Optional.of(order));

        OrderEntity result = orderService.findById(anyLong()).orElse(null);

        assertEquals(result.getId(), order.getId());
    }

    @Test
    void findByUserIdAndActive() {
        OrderEntity order = OrderEntity.builder().id(1L)
                .products(Collections.EMPTY_LIST).price(BigDecimal.TEN).quantity(20).build();
        when(orderRepository.findByUserIdAndActive(anyLong(), anyBoolean()))
                .thenReturn(Collections.singletonList(order));

        List<OrderEntity> orders = orderService.findByUserIdAndActive(anyLong(), anyBoolean());

        assertEquals(orders.get(0).getId(),order.getId());
    }
}