package com.sumber.barokah.jurnal.service;

import com.sumber.barokah.jurnal.dto.master.CreateCustomerRequest;
import com.sumber.barokah.jurnal.dto.master.CustomerResponse;
import com.sumber.barokah.jurnal.dto.master.PageableRequest;
import com.sumber.barokah.jurnal.dto.master.UpdateCustomerRequest;
import org.springframework.data.domain.Page;

import java.util.List;

public interface CustomerService {

    CustomerResponse create(CreateCustomerRequest request);

    List<CustomerResponse> listCustomer();

    Page<CustomerResponse> listCustomerPageableStatic(PageableRequest request);

    Page<CustomerResponse> listCustomerPageableDynamic(PageableRequest request);

    CustomerResponse get(String id);

    CustomerResponse update(UpdateCustomerRequest request);

    void delete(String id);




}
