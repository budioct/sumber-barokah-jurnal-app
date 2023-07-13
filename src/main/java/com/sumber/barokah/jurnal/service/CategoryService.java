package com.sumber.barokah.jurnal.service;

import com.sumber.barokah.jurnal.dto.master.CategoryResponse;
import com.sumber.barokah.jurnal.dto.master.CreateCategoryRequest;
import com.sumber.barokah.jurnal.dto.master.UpdateCategoryRequest;

import java.util.List;

public interface CategoryService {

    CategoryResponse create(CreateCategoryRequest request);

    List<CategoryResponse> listCategory();

    CategoryResponse get(String id);

    CategoryResponse update(UpdateCategoryRequest request);

    void delete(String id);

}
