package com.sumber.barokah.jurnal.service;

import com.sumber.barokah.jurnal.dto.master.CreateCustomerRequest;
import com.sumber.barokah.jurnal.dto.master.CustomerResponse;
import com.sumber.barokah.jurnal.dto.master.PageableCustomerRequest;
import com.sumber.barokah.jurnal.dto.master.UpdateCustomerRequest;
import org.springframework.data.domain.Page;

import java.util.List;

public interface CustomerService {

    CustomerResponse create(CreateCustomerRequest request);

    List<CustomerResponse> listCustomer();

    Page<CustomerResponse> listCustomerPageableStatic(PageableCustomerRequest request);

    Page<CustomerResponse> listCustomerPageableDynamic(PageableCustomerRequest request);

    CustomerResponse get(String id);

    CustomerResponse update(UpdateCustomerRequest request);

    void delete(String id);




}
