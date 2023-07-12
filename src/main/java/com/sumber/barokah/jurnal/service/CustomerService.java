package com.sumber.barokah.jurnal.service;

import com.sumber.barokah.jurnal.dto.master.CreateCustomerRequest;
import com.sumber.barokah.jurnal.dto.master.CustomerResponse;

import java.util.List;

public interface CustomerService {

    CustomerResponse create(CreateCustomerRequest request);

    List<CustomerResponse> listCustomer();

    CustomerResponse get(String id);


}
