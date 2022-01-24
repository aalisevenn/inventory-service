package com.aliseven.inventoryservice.service;


import com.aliseven.inventoryservice.business.CategoryServiceImpl;
import com.aliseven.inventoryservice.business.iface.CategoryService;
import com.aliseven.inventoryservice.model.entity.CategoryEntity;
import com.aliseven.inventoryservice.model.repository.CategoryRepository;
import com.aliseven.inventoryservice.model.request.CreateCategoryRequestVO;
import com.aliseven.inventoryservice.model.response.CreateCategoryResponseVO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class CategoryServiceImplTest {

    private CategoryService categoryService;

    private CategoryRepository categoryRepository;
    @MockBean
    private Authentication authentication;
    @MockBean
    private SecurityContext securityContext;

    @BeforeEach
    public void setUp() {
        categoryRepository = mock(CategoryRepository.class);
        authentication = mock(Authentication.class);
        securityContext = mock(SecurityContext.class);
        categoryService = new CategoryServiceImpl(categoryRepository);
    }

    @Test
    void saveCategory() {
        CreateCategoryRequestVO request = CreateCategoryRequestVO.builder().name("testName").description("testName Books").build();
        when(securityContext.getAuthentication()).thenReturn(authentication);
        SecurityContextHolder.setContext(securityContext);
        when(SecurityContextHolder.getContext().getAuthentication().getName()).thenReturn("ali");
        when(categoryRepository.save(any())).thenReturn(
                CategoryEntity.builder()
                        .products(Collections.emptyList())
                        .name("testName")
                        .build());

        CreateCategoryResponseVO response = categoryService.save(request);
        assertEquals(response.getName(), request.getName());

    }

    @Test
    public void testFindById_whenBookIdExists_shouldReturnBook() {
        CategoryEntity category = CategoryEntity.builder().id(1L).name("testName").build();

        when(categoryRepository.findById(anyLong())).thenReturn(Optional.of(category));

        CategoryEntity result = categoryService.findById(anyLong()).orElse(null);

        assertEquals(result.getId(), category.getId());
    }

    @Test
    public void testFindById_whenBookIdDoesNotExists_shouldReturnEmpty() {

        when(categoryRepository.findById(anyLong())).thenReturn(Optional.empty());

        Optional<CategoryEntity> result = categoryService.findById(anyLong());

        assertEquals(result, Optional.empty());
    }
}