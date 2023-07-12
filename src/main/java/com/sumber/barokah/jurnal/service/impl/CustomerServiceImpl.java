package com.sumber.barokah.jurnal.service.impl;

import com.sumber.barokah.jurnal.dto.master.CreateCustomerRequest;
import com.sumber.barokah.jurnal.dto.master.CustomerResponse;
import com.sumber.barokah.jurnal.dto.master.UpdateCustomerRequest;
import com.sumber.barokah.jurnal.entity.master.Customer;
import com.sumber.barokah.jurnal.repository.CustomerRepository;
import com.sumber.barokah.jurnal.service.CustomerService;
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

    @Transactional(readOnly = true)
    public List<CustomerResponse> listCustomer() {

        List<Customer> list = customerRepository.findAll();

        if (Objects.isNull(list)){
           throw new ResponseStatusException(HttpStatus.NO_CONTENT, "Customer content does not exist!");
        }

        return list.stream().map(this::toCustomerResponse).collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public CustomerResponse get(String id) {

        Customer customer = customerRepository.findFirstByCustomerId(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Customer not found"));

        return toCustomerResponse(customer);
    }

    @Transactional
    public CustomerResponse update(UpdateCustomerRequest request) {

        validationService.validate(request);

        Customer cs = customerRepository.findFirstByCustomerId(request.getCustomerId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Customer not found"));

        cs.setName(request.getName());
        cs.setCompany(request.getCompany());
        cs.setSaldo(request.getSaldo());
        cs.setNoHPhone(request.getNoHPhone());
        cs.setEmail(request.getEmail());
        cs.setAddress(request.getAddress());

        customerRepository.save(cs); // save DB

        return toCustomerResponse(cs);

    }

    @Transactional
    public void delete(String id) {

        Customer cs = customerRepository.findFirstByCustomerId(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Customer not found"));

        customerRepository.delete(cs); // delete DB

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
