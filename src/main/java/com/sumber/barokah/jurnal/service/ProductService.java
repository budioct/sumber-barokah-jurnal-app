package com.sumber.barokah.jurnal.service;

import com.sumber.barokah.jurnal.dto.master.CreateProductRequest;
import com.sumber.barokah.jurnal.dto.master.PageableRequest;
import com.sumber.barokah.jurnal.dto.master.ProductResponse;
import com.sumber.barokah.jurnal.dto.master.UpdateProductRequest;
import org.springframework.data.domain.Page;

import java.util.List;

public interface ProductService {

    ProductResponse create(CreateProductRequest request);

    List<ProductResponse> listProduct();

    Page<ProductResponse> listPageable(PageableRequest request);

    ProductResponse get(String categoryId, String productId);

    ProductResponse update(UpdateProductRequest request);

    void delete(String categoryId, String productId);

}
