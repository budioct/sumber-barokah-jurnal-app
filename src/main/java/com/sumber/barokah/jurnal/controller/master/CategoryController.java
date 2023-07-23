package com.sumber.barokah.jurnal.controller.master;

import com.sumber.barokah.jurnal.dto.PagingResponse;
import com.sumber.barokah.jurnal.dto.WebResponse;
import com.sumber.barokah.jurnal.dto.master.CategoryResponse;
import com.sumber.barokah.jurnal.dto.master.CreateCategoryRequest;
import com.sumber.barokah.jurnal.dto.master.PageableRequest;
import com.sumber.barokah.jurnal.dto.master.UpdateCategoryRequest;
import com.sumber.barokah.jurnal.service.CategoryService;
import com.sumber.barokah.jurnal.utilities.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
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
    public WebResponse<CategoryResponse> create(@RequestBody CreateCategoryRequest request) {

        CategoryResponse categoryResponse = categoryService.create(request);

        return WebResponse.<CategoryResponse>builder()
                .data(categoryResponse)
                .status(HttpStatus.OK)
                .status_code(Constants.OK)
                .message(Constants.CREATE_MESSAGE)
                .build();

    }

    @GetMapping(
            path = "/api/sb/categories",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public WebResponse<List<CategoryResponse>> list() {

        List<CategoryResponse> categoryResponses = categoryService.listCategory();

        return WebResponse.<List<CategoryResponse>>builder()
                .data(categoryResponses)
                .status(HttpStatus.OK)
                .status_code(Constants.OK)
                .message(Constants.ITEM_EXIST_MESSAGE)
                .build();

    }

    @GetMapping(
            path = "/api/sb/{id}/categories",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public WebResponse<CategoryResponse> get(@PathVariable(name = "id") String id) {

        CategoryResponse categoryResponse = categoryService.get(id);

        return WebResponse.<CategoryResponse>builder()
                .data(categoryResponse)
                .status(HttpStatus.OK)
                .status_code(Constants.OK)
                .message(Constants.ITEM_EXIST_MESSAGE)
                .build();

    }

    @PutMapping(
            path = "/api/sb/{id}/categories",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public WebResponse<CategoryResponse> update(@PathVariable(name = "id") String id,
                                                @RequestBody UpdateCategoryRequest request) {

        request.setCategoryId(id);
        CategoryResponse categoryResponse = categoryService.update(request);

        return WebResponse.<CategoryResponse>builder()
                .data(categoryResponse)
                .status(HttpStatus.OK)
                .status_code(Constants.OK)
                .message(Constants.UPDATE_MESSAGE)
                .build();
    }

    @DeleteMapping(
            path = "/api/sb/{id}/categories",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public WebResponse<String> delete(@PathVariable(name = "id") String id) {

        categoryService.delete(id);

        return WebResponse.<String>builder()
                .data("")
                .status(HttpStatus.OK)
                .status_code(Constants.OK)
                .message(Constants.DELETE_MESSAGE)
                .build();

    }



    @GetMapping(
            path = "/api/sb/categories1",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public WebResponse<List<CategoryResponse>> listPagable(@RequestParam(name = "sortField", required = false) String sortField,
                                                           @RequestParam(name = "page", required = false, defaultValue = "0") Integer page,
                                                           @RequestParam(name = "size", required = false, defaultValue = "10") Integer size
    ) {

        PageableRequest request = new PageableRequest();
        request.setPage(page);
        request.setSize(size);
        request.setSortField(sortField);

        Page<CategoryResponse> categoryResponses = categoryService.listPagable(request);

        return WebResponse.<List<CategoryResponse>>builder()
                .data(categoryResponses.getContent())
                .paging(PagingResponse.builder()
                        .currentPage(categoryResponses.getNumber())
                        .totalPage(categoryResponses.getTotalPages())
                        .size(categoryResponses.getSize())
                        .build())
                .build();

    }


}
