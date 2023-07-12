package com.sumber.barokah.jurnal.service.impl;

import com.sumber.barokah.jurnal.dto.master.CreateCustomerRequest;
import com.sumber.barokah.jurnal.dto.master.CustomerResponse;
import com.sumber.barokah.jurnal.entity.master.Customer;
import com.sumber.barokah.jurnal.repository.CustomerRepository;
import com.sumber.barokah.jurnal.service.CustomerService;
import com.sumber.barokah.jurnal.service.ValidationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
public class CustomerServiceImpl implements CustomerService {

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private ValidationService validationService;

    @Transactional
    public CustomerResponse create(CreateCustomerRequest request) {

        validationService.validate(request);

        Customer cs = new Customer();
        cs.setCustomerId(UUID.randomUUID().toString());
        cs.setName(request.getName());
        cs.setCompany(request.getCompany());
        cs.setSaldo(request.getSaldo());
        cs.setNoHPhone(request.getNoHPhone());
        cs.setEmail(request.getEmail());
        cs.setAddress(request.getAddress());

        customerRepository.save(cs); // save DB

        return toCustomerResponse(cs);
    }

    private CustomerResponse toCustomerResponse(Customer customer){
        return CustomerResponse.builder()
                .customerId(customer.getCustomerId())
                .name(customer.getName())
                .company(customer.getCompany())
                .saldo(customer.getSaldo())
                .noHPhone(customer.getNoHPhone())
                .email(customer.getEmail())
                .address(customer.getAddress())
                .createAt(customer.getCreateAt())
                .updateModifiedAt(customer.getUpdateModifiedAt())
                .build(); // response
    }

}
