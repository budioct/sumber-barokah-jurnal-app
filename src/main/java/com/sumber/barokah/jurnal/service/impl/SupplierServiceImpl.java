package com.sumber.barokah.jurnal.service.impl;

import com.sumber.barokah.jurnal.dto.master.CreateSupplierRequest;
import com.sumber.barokah.jurnal.dto.master.SupplierResponse;
import com.sumber.barokah.jurnal.dto.master.UpdateSupplierRequest;
import com.sumber.barokah.jurnal.entity.master.Supplier;
import com.sumber.barokah.jurnal.repository.master.SupplierRepository;
import com.sumber.barokah.jurnal.service.SupplierService;
import com.sumber.barokah.jurnal.service.ValidationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.function.Function;
import java.util.stream.Collectors;

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
        sup.setNoHPhone(request.getNoHPhone());
        sup.setEmail(request.getEmail());
        sup.setAddress(request.getAddress());

        supplierRepository.save(sup); // save DB

        return toSupplierResponse(sup);
    }

    @Transactional(readOnly = true)
    public List<SupplierResponse> list() {

        List<Supplier> list = supplierRepository.findAll();

        if (Objects.isNull(list)) {
            throw new ResponseStatusException(HttpStatus.NO_CONTENT, "Supplier content does not exist!");
        }

        return list.stream().map(this::toSupplierResponse).collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public SupplierResponse get(String id) {

        Supplier supplier = supplierRepository.findFirstBySupplierId(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Supplier not found"));

        return toSupplierResponse(supplier);
    }

    @Transactional
    public SupplierResponse update(UpdateSupplierRequest request) {

        validationService.validate(request);

        Supplier sup = supplierRepository.findFirstBySupplierId(request.getSupplierId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Supplier not found"));

        sup.setName(request.getName());
        sup.setCompany(request.getCompany());
        sup.setSaldo(request.getSaldo());
        sup.setNoHPhone(request.getNoHPhone());
        sup.setEmail(request.getEmail());
        sup.setAddress(request.getAddress());

        supplierRepository.save(sup); // save DB

        return toSupplierResponse(sup);
    }

    @Transactional
    public void delete(String id) {

        Supplier supplier = supplierRepository.findFirstBySupplierId(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Supplier not found"));

        supplierRepository.delete(supplier);

    }

    private SupplierResponse toSupplierResponse(Supplier supplier) {
        return SupplierResponse.builder()
                .supplierId(supplier.getSupplierId())
                .name(supplier.getName())
                .company(supplier.getCompany())
                .saldo(supplier.getSaldo())
                .company(supplier.getCompany())
                .email(supplier.getEmail())
                .noHPhone(supplier.getNoHPhone())
                .address(supplier.getAddress())
                .createAt(supplier.getCreateAt())
                .updateModifiedAt(supplier.getUpdateModifiedAt())
                .build();
    }

}