package com.sumber.barokah.jurnal.controller;

import com.sumber.barokah.jurnal.dto.WebResponse;
import com.sumber.barokah.jurnal.dto.master.CreateProductRequest;
import com.sumber.barokah.jurnal.dto.master.ProductResponse;
import com.sumber.barokah.jurnal.dto.master.UpdateProductRequest;
import com.sumber.barokah.jurnal.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ProductController {

    @Autowired
    ProductService productService;

    @PostMapping(
            path = "/api/sb/categories/{id}/products",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public WebResponse<ProductResponse> create(@PathVariable(name = "id") String id,
                                               @RequestBody CreateProductRequest request) {

        request.setCategoryId(id);
        ProductResponse productResponse = productService.create(request);

        return WebResponse.<ProductResponse>builder().data(productResponse).build();
    }

    @GetMapping(
            path = "/api/sb/products",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public WebResponse<List<ProductResponse>> list() {

        List<ProductResponse> productResponses = productService.listProduct();

        return WebResponse.<List<ProductResponse>>builder().data(productResponses).build();

    }

    @GetMapping(
            path = "/api/sb/categories/{categoryId}/products/{productId}",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public WebResponse<ProductResponse> get(@PathVariable(name = "categoryId") String categoryId,
                                            @PathVariable(name = "productId") String productId){

        ProductResponse productResponse = productService.get(categoryId, productId);

        return WebResponse.<ProductResponse>builder().data(productResponse).build();

    }

    @PutMapping(
            path = "/api/sb/categories/{categoryId}/products/{productId}",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public WebResponse<ProductResponse> update(@PathVariable(name = "categoryId") String categoryId,
                                               @PathVariable(name = "productId") String productId,
                                               @RequestBody UpdateProductRequest request){

        request.setCategoryId(categoryId);
        request.setProductId(productId);

        ProductResponse productResponse = productService.update(request);

        return WebResponse.<ProductResponse>builder().data(productResponse).build();

    }

    @DeleteMapping(
            path = "/api/sb/categories/{categoryId}/products/{productId}",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public WebResponse<String> delete(@PathVariable(name = "categoryId") String categoryId,
                                      @PathVariable(name = "productId") String productId){

        productService.delete(categoryId, productId);

        return WebResponse.<String>builder().data("OK").build();

    }


}
