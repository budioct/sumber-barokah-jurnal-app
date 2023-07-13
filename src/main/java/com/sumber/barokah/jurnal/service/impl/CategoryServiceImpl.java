package com.sumber.barokah.jurnal.service.impl;

import com.sumber.barokah.jurnal.dto.master.CategoryResponse;
import com.sumber.barokah.jurnal.dto.master.CreateCategoryRequest;
import com.sumber.barokah.jurnal.entity.master.Category;
import com.sumber.barokah.jurnal.repository.master.CategoryRepository;
import com.sumber.barokah.jurnal.service.CategoryService;
import com.sumber.barokah.jurnal.service.ValidationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    ValidationService validationService;

    @Autowired
    CategoryRepository categoryRepository;

    @Transactional
    public CategoryResponse create(CreateCategoryRequest request) {

        validationService.validate(request);

        Category cat = new Category();
        cat.setCategoryId(UUID.randomUUID().toString());
        cat.setName(request.getName());

        categoryRepository.save(cat);

        return toCategoryResponse(cat);
    }

    private CategoryResponse toCategoryResponse(Category category){
        return CategoryResponse.builder()
                .categoryId(category.getCategoryId())
                .name(category.getName())
                .products(category.getProducts())
                .createAt(category.getCreateAt())
                .updateModifiedAt(category.getUpdateModifiedAt())
                .build();
    }

}
