package com.sumber.barokah.jurnal.controller;

import com.sumber.barokah.jurnal.dto.WebResponse;
import com.sumber.barokah.jurnal.dto.master.CategoryResponse;
import com.sumber.barokah.jurnal.dto.master.CreateCategoryRequest;
import com.sumber.barokah.jurnal.dto.master.UpdateCategoryRequest;
import com.sumber.barokah.jurnal.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @PostMapping(
            path = "/api/sb/categories",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public WebResponse<CategoryResponse> create(@RequestBody CreateCategoryRequest request){

        CategoryResponse categoryResponse = categoryService.create(request);

        return WebResponse.<CategoryResponse>builder().data(categoryResponse).build();

    }

    @GetMapping(
            path = "/api/sb/categories",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public WebResponse<List<CategoryResponse>> list(){

        List<CategoryResponse> list = categoryService.list();

        return WebResponse.<List<CategoryResponse>>builder().data(list).build();

    }

    @GetMapping(
            path = "/api/sb/{id}/categories",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public WebResponse<CategoryResponse> get(@PathVariable(name = "id") String id){

        CategoryResponse categoryResponse = categoryService.get(id);

        return WebResponse.<CategoryResponse>builder().data(categoryResponse).build();

    }

    @PutMapping(
            path = "/api/sb/{id}/categories",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public WebResponse<CategoryResponse> update(@PathVariable(name = "id") String id,
                                                @RequestBody UpdateCategoryRequest request){

        request.setCategoryId(id);
        CategoryResponse categoryResponse = categoryService.update(request);

        return WebResponse.<CategoryResponse>builder().data(categoryResponse).build();
    }


}
