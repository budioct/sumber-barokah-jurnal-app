package com.sumber.barokah.jurnal.service.impl;

import com.sumber.barokah.jurnal.dto.master.CreateSupplierRequest;
import com.sumber.barokah.jurnal.dto.master.SupplierResponse;
import com.sumber.barokah.jurnal.entity.master.Supplier;
import com.sumber.barokah.jurnal.repository.master.SupplierRepository;
import com.sumber.barokah.jurnal.service.SupplierService;
import com.sumber.barokah.jurnal.service.ValidationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
public class SupplierServiceImpl implements SupplierService {

    @Autowired
    ValidationService validationService;

    @Autowired
    SupplierRepository supplierRepository;

    @Transactional
    public SupplierResponse create(CreateSupplierRequest request) {

        validationService.validate(request);

        Supplier sup = new Supplier();
        sup.setSupplierId(UUID.randomUUID().toString());
        sup.setName(request.getName());
        sup.setCompany(request.getCompany());
        sup.setSaldo(request.getSaldo());
        sup.setCompany(request.getCompany());
        sup.setEmail(request.getEmail());
        sup.setNoHPhone(request.getNoHPhone());
        sup.setAddress(request.getAddress());

        supplierRepository.save(sup); // save DB

        return toSupplierResponse(sup);
    }

    private SupplierResponse toSupplierResponse(Supplier supplier){
        return SupplierResponse.builder()
                .supplierId(supplier.getSupplierId())
                .name(supplier.getName())
                .company(supplier.getCompany())
                .saldo(supplier.getSaldo())
                .company(supplier.getCompany())
                .email(supplier.getEmail())
                .noHPhone(supplier.getNoHPhone())
                .address(supplier.getAddress())
                .build();
    }

}
