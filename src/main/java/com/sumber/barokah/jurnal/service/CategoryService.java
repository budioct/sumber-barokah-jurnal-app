package com.sumber.barokah.jurnal.service;

import com.sumber.barokah.jurnal.dto.master.CategoryResponse;
import com.sumber.barokah.jurnal.dto.master.CreateCategoryRequest;

public interface CategoryService {

    CategoryResponse create(CreateCategoryRequest request);

}
