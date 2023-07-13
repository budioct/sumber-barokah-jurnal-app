package com.sumber.barokah.jurnal.controller;

import com.sumber.barokah.jurnal.dto.WebResponse;
import com.sumber.barokah.jurnal.dto.master.CreateSupplierRequest;
import com.sumber.barokah.jurnal.dto.master.SupplierResponse;
import com.sumber.barokah.jurnal.service.SupplierService;
import org.springframework.beans.factory.annotation.Autowired;
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

        return WebResponse.<SupplierResponse>builder().data(supplierResponse).build();

    }

    @GetMapping(
            path = "/api/sb/suppliers",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public WebResponse<List<SupplierResponse>> list() {

        List<SupplierResponse> list = supplierService.list();

        return WebResponse.<List<SupplierResponse>>builder().data(list).build();

    }

    @GetMapping(
            path = "/api/sb/{id}/suppliers",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public WebResponse<SupplierResponse> get(@PathVariable(name = "id") String id) {

        SupplierResponse supplierResponse = supplierService.get(id);

        return WebResponse.<SupplierResponse>builder().data(supplierResponse).build();

    }


}
