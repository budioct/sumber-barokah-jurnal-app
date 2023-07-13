package com.sumber.barokah.jurnal.controller;

import com.sumber.barokah.jurnal.dto.WebResponse;
import com.sumber.barokah.jurnal.dto.master.CreateProductRequest;
import com.sumber.barokah.jurnal.dto.master.ProductResponse;
import com.sumber.barokah.jurnal.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ProductController {

    @Autowired
    ProductService productService;

    @PostMapping(
            path = "/api/sb/categories/{id}/products"
    )
    public WebResponse<ProductResponse> create(@PathVariable(name = "id") String id,
                                               @RequestBody CreateProductRequest request){

        request.setCategoryId(id);
        ProductResponse productResponse = productService.create(request);

        return WebResponse.<ProductResponse>builder().data(productResponse).build();
    }

}
