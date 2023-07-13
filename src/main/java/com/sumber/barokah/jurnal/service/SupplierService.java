package com.sumber.barokah.jurnal.service;

import com.sumber.barokah.jurnal.dto.master.CreateSupplierRequest;
import com.sumber.barokah.jurnal.dto.master.SupplierResponse;

import java.util.List;

public interface SupplierService {

    SupplierResponse create(CreateSupplierRequest request);

}
