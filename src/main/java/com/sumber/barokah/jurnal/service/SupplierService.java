package com.sumber.barokah.jurnal.service;

import com.sumber.barokah.jurnal.dto.master.CreateSupplierRequest;
import com.sumber.barokah.jurnal.dto.master.PageableRequest;
import com.sumber.barokah.jurnal.dto.master.SupplierResponse;
import com.sumber.barokah.jurnal.dto.master.UpdateSupplierRequest;
import org.springframework.data.domain.Page;

import java.util.List;

public interface SupplierService {

    SupplierResponse create(CreateSupplierRequest request);

    List<SupplierResponse> list();

    Page<SupplierResponse> listPageable(PageableRequest request);

    SupplierResponse get(String id);

    SupplierResponse update(UpdateSupplierRequest request);

    void delete(String id);

}
