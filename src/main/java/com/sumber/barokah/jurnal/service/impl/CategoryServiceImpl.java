package com.sumber.barokah.jurnal.service.impl;

import com.sumber.barokah.jurnal.dto.master.CategoryResponse;
import com.sumber.barokah.jurnal.dto.master.CreateCategoryRequest;
import com.sumber.barokah.jurnal.dto.master.UpdateCategoryRequest;
import com.sumber.barokah.jurnal.entity.master.Category;
import com.sumber.barokah.jurnal.repository.master.CategoryRepository;
import com.sumber.barokah.jurnal.service.CategoryService;
import com.sumber.barokah.jurnal.service.ValidationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.function.Function;
import java.util.stream.Collectors;

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

        categoryRepository.save(cat); // save DB

        return toCategoryResponse(cat);
    }

    @Transactional(readOnly = true)
    public List<CategoryResponse> list() {

        List<Category> list = categoryRepository.findAll();

        if (Objects.isNull(list)) {
            throw new ResponseStatusException(HttpStatus.NO_CONTENT, "Category content does not exist!");
        }

        return list.stream().map(this::toCategoryResponse).collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public CategoryResponse get(String id) {

        Category cat = categoryRepository.findFirstByCategoryId(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Category not found"));

        return toCategoryResponse(cat);
    }

    @Transactional
    public CategoryResponse update(UpdateCategoryRequest request) {

        validationService.validate(request);

        Category cat = categoryRepository.findFirstByCategoryId(request.getCategoryId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Category not found"));

        cat.setName(request.getName());

        categoryRepository.save(cat); // save DB

        return toCategoryResponse(cat);
    }

    @Transactional
    public void delete(String id) {

        Category cat = categoryRepository.findFirstByCategoryId(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Category not found"));

        categoryRepository.delete(cat);

    }

    private CategoryResponse toCategoryResponse(Category category) {
        return CategoryResponse.builder()
                .categoryId(category.getCategoryId())
                .name(category.getName())
                .products(category.getProducts())
                .createAt(category.getCreateAt())
                .updateModifiedAt(category.getUpdateModifiedAt())
                .build();
    }

}
