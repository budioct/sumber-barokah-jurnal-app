package com.sumber.barokah.jurnal.controller.master;

import com.sumber.barokah.jurnal.dto.PagingResponse;
import com.sumber.barokah.jurnal.dto.WebResponse;
import com.sumber.barokah.jurnal.dto.master.*;
import com.sumber.barokah.jurnal.service.SupplierService;
import com.sumber.barokah.jurnal.utilities.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class SupplierController {

    @Autowired
    SupplierService supplierService;

    @PostMapping(
            path = "/api/sb/suppliers",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public WebResponse<SupplierResponse> create(@RequestBody CreateSupplierRequest request) {

        SupplierResponse supplierResponse = supplierService.create(request);

        return WebResponse.<SupplierResponse>builder()
                .data(supplierResponse)
                .status(HttpStatus.OK)
                .status_code(Constants.OK)
                .message(Constants.CREATE_MESSAGE)
                .build();

    }

    @GetMapping(
            path = "/api/sb/suppliers",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public WebResponse<List<SupplierResponse>> list() {

        List<SupplierResponse> list = supplierService.list();

        return WebResponse.<List<SupplierResponse>>builder()
                .data(list)
                .status(HttpStatus.OK)
                .status_code(Constants.OK)
                .message(Constants.ITEM_EXIST_MESSAGE)
                .build();

    }

    @GetMapping(
            path = "/api/sb/{id}/suppliers",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public WebResponse<SupplierResponse> get(@PathVariable(name = "id") String id) {

        SupplierResponse supplierResponse = supplierService.get(id);

        return WebResponse.<SupplierResponse>builder()
                .data(supplierResponse)
                .status(HttpStatus.OK)
                .status_code(Constants.OK)
                .message(Constants.ITEM_EXIST_MESSAGE)
                .build();

    }

    @PutMapping(
            path = "/api/sb/{id}/suppliers",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public WebResponse<SupplierResponse> update(@PathVariable(name = "id") String id,
                                                @RequestBody UpdateSupplierRequest request) {

        request.setSupplierId(id);
        SupplierResponse supplierResponse = supplierService.update(request);

        return WebResponse.<SupplierResponse>builder()
                .data(supplierResponse)
                .status(HttpStatus.OK)
                .status_code(Constants.OK)
                .message(Constants.UPDATE_MESSAGE)
                .build();

    }

    @DeleteMapping(
            path = "/api/sb/{id}/suppliers",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public WebResponse<String> delete(@PathVariable(name = "id") String id) {

        supplierService.delete(id);

        return WebResponse.<String>builder()
                .data("")
                .status(HttpStatus.OK)
                .status_code(Constants.OK)
                .message(Constants.DELETE_MESSAGE)
                .build();

    }

    @GetMapping(
            path = "/api/sb/suppliers1",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public WebResponse<List<SupplierResponse>> listPagable(@RequestParam(name = "sortField", required = false) String sortField,
                                                           @RequestParam(name = "page", required = false, defaultValue = "0") Integer page,
                                                           @RequestParam(name = "size", required = false, defaultValue = "10") Integer size
    ) {

        PageableRequest request = new PageableRequest();
        request.setPage(page);
        request.setSize(size);
        request.setSortField(sortField);

        Page<SupplierResponse> supplierResponses = supplierService.listPageable(request);

        return WebResponse.<List<SupplierResponse>>builder()
                .data(supplierResponses.getContent())
                .paging(PagingResponse.builder()
                        .currentPage(supplierResponses.getNumber())
                        .totalPage(supplierResponses.getTotalPages())
                        .size(supplierResponses.getSize())
                        .build())
                .build();

    }

}
