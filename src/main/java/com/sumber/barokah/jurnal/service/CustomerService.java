package com.sumber.barokah.jurnal.service;

import com.sumber.barokah.jurnal.dto.master.CreateCustomerRequest;
import com.sumber.barokah.jurnal.dto.master.CustomerResponse;

public interface CustomerService {

    CustomerResponse create(CreateCustomerRequest request);

}
