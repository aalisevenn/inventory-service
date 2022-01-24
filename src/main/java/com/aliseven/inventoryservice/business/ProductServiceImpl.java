package com.aliseven.inventoryservice.business;

import com.aliseven.inventoryservice.business.iface.CategoryService;
import com.aliseven.inventoryservice.business.iface.ProductService;
import com.aliseven.inventoryservice.exception.BusinessException;
import com.aliseven.inventoryservice.exception.ExceptionConstants;
import com.aliseven.inventoryservice.model.entity.CategoryEntity;
import com.aliseven.inventoryservice.model.entity.ProductEntity;
import com.aliseven.inventoryservice.model.repository.ProductRepository;
import com.aliseven.inventoryservice.model.request.CreateProductRequestVO;
import com.aliseven.inventoryservice.model.response.CreateProductResponseVO;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final CategoryService categoryService;

    public ProductServiceImpl(ProductRepository productRepository, CategoryService categoryService) {
        this.productRepository = productRepository;
        this.categoryService = categoryService;
    }


    @Override
    public CreateProductResponseVO save(CreateProductRequestVO request) {
        final CategoryEntity category = categoryService.findById(request.getCategoryId())
                .orElseThrow(() -> new BusinessException("Category not found!", ExceptionConstants.CATEGORY_NOT_FOUND));
        final String user = SecurityContextHolder.getContext().getAuthentication().getName();
        ProductEntity savedBook = productRepository.save(ProductEntity.builder()
                .category(category)
                .name(request.getName())
                .price(request.getPrice())
                .updateUser(user)
                .build());

        return CreateProductResponseVO.builder()
                .id(savedBook.getId())
                .name(savedBook.getName())
                .price(savedBook.getPrice())
                .updatedDate(savedBook.getUpdatedDate())
                .updatedUser(savedBook.getUpdateUser())
                .category(savedBook.getCategory().getId())
                .build();
    }

    @Override
    public Optional<ProductEntity> findById(Long id) {
        return productRepository.findById(id);
    }


}

