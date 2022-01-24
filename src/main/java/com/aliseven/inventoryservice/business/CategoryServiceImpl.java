package com.aliseven.inventoryservice.business;

import com.aliseven.inventoryservice.business.iface.CategoryService;
import com.aliseven.inventoryservice.model.entity.CategoryEntity;
import com.aliseven.inventoryservice.model.repository.CategoryRepository;
import com.aliseven.inventoryservice.model.request.CreateCategoryRequestVO;
import com.aliseven.inventoryservice.model.response.CreateCategoryResponseVO;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CategoryServiceImpl implements CategoryService {


    private final CategoryRepository categoryRepository;

    public CategoryServiceImpl(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }


    @Override
    public CreateCategoryResponseVO save(CreateCategoryRequestVO request) {
        final String user = SecurityContextHolder.getContext().getAuthentication().getName();
        CategoryEntity categoryIndb = categoryRepository.save(CategoryEntity.builder()
                .name(request.getName())
                .description(request.getDescription())
                .updateUser(user)
                .build());
        return CreateCategoryResponseVO.builder()
                .id(categoryIndb.getId())
                .name(categoryIndb.getName())
                .description(categoryIndb.getDescription())
                .updatedDate(categoryIndb.getUpdatedDate())
                .updatedUser(categoryIndb.getUpdateUser())
                .build();
    }

    public Optional<CategoryEntity> findById(Long id) {
        return categoryRepository.findById(id);
    }
}
