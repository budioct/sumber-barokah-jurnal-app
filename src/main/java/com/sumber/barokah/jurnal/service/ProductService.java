package com.sumber.barokah.jurnal.service;

import com.sumber.barokah.jurnal.dto.master.CreateProductRequest;
import com.sumber.barokah.jurnal.dto.master.ProductResponse;

import java.util.List;

public interface ProductService {

    ProductResponse create(CreateProductRequest request);

    List<ProductResponse> listProduct();

}
