package com.aliseven.inventoryservice.service;

import com.aliseven.inventoryservice.business.ProductServiceImpl;
import com.aliseven.inventoryservice.business.iface.CategoryService;
import com.aliseven.inventoryservice.business.iface.ProductService;
import com.aliseven.inventoryservice.exception.BusinessException;
import com.aliseven.inventoryservice.model.entity.CategoryEntity;
import com.aliseven.inventoryservice.model.entity.ProductEntity;
import com.aliseven.inventoryservice.model.repository.ProductRepository;

import com.aliseven.inventoryservice.model.request.CreateProductRequestVO;
import com.aliseven.inventoryservice.model.response.CreateProductResponseVO;
import com.aliseven.inventoryservice.model.response.CreateStockResponseVO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ProductServiceImplTest {

    private ProductService bookService;
    private ProductRepository bookRepository;
    private CategoryService categoryService;
    @MockBean
    private Authentication authentication;
    @MockBean
    private SecurityContext securityContext;

    @BeforeEach
    public void setUp() {
        bookRepository = mock(ProductRepository.class);
        categoryService = mock(CategoryService.class);
        authentication = mock(Authentication.class);
        securityContext = mock(SecurityContext.class);
        bookService = new ProductServiceImpl(bookRepository, categoryService);

    }

    @Test
    public void testSaveBook_whenCategoryExists_ShouldReturnSucces() {
        CreateProductRequestVO request = new CreateProductRequestVO("World One", BigDecimal.TEN, 1L, LocalDateTime.now(), "ali");
        ProductEntity book = ProductEntity.builder().name("World One").id(1L).category(new CategoryEntity()).build();

        when(securityContext.getAuthentication()).thenReturn(authentication);

        SecurityContextHolder.setContext(securityContext);

        when(categoryService.findById(anyLong())).thenReturn(Optional.of(new CategoryEntity()));
        when(SecurityContextHolder.getContext().getAuthentication().getName()).thenReturn("ali");

        when(bookRepository.save(any())).thenReturn(book);

        CreateProductResponseVO result = bookService.save(request);
        assertEquals(result.getName(), request.getName());


    }

    @Test
    public void testSaveBook_whenCategoryDoesNotExists_ShouldReturnBusinessException() {
        CreateProductRequestVO request = CreateProductRequestVO.builder().name("World One").build();
        assertThrows(BusinessException.class, () -> bookService.save(request));

    }

    @Test
    public void testFindById_whenBookIdExists_shouldReturnBook() {
        ProductEntity book = ProductEntity.builder().id(1L).name("World One").build();

        when(bookRepository.findById(anyLong())).thenReturn(Optional.of(book));

        ProductEntity result = bookService.findById(anyLong()).orElse(null);

        assertEquals(result.getId(), book.getId());
    }

    @Test
    public void testFindById_whenBookIdDoesNotExists_shouldReturnEmpty() {

        when(bookRepository.findById(anyLong())).thenReturn(Optional.empty());

        Optional<ProductEntity> result = bookService.findById(anyLong());

        assertEquals(result, Optional.empty());
    }
}