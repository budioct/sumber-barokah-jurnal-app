package com.sumber.barokah.jurnal.service;

import com.sumber.barokah.jurnal.dto.master.CreateProductRequest;
import com.sumber.barokah.jurnal.dto.master.ProductResponse;

public interface ProductService {

    ProductResponse create(CreateProductRequest request);

}
