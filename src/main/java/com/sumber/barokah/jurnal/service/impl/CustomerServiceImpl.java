package com.sumber.barokah.jurnal.service.impl;

import com.sumber.barokah.jurnal.dto.master.CreateCustomerRequest;
import com.sumber.barokah.jurnal.dto.master.CustomerResponse;
import com.sumber.barokah.jurnal.dto.master.PageableCustomerRequest;
import com.sumber.barokah.jurnal.dto.master.UpdateCustomerRequest;
import com.sumber.barokah.jurnal.entity.master.Customer;
import com.sumber.barokah.jurnal.repository.master.CustomerRepository;
import com.sumber.barokah.jurnal.service.CustomerService;
import com.sumber.barokah.jurnal.service.ValidationService;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.function.Function;
import java.util.stream.Collectors;

@Slf4j
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

        if (Objects.isNull(list)) {
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

    @Transactional(readOnly = true)
    public Page<CustomerResponse> listCustomerPageableStatic(PageableCustomerRequest request) {
        // manual 10 size of page optional. with sorted asc field createAt entity
        PageRequest pageable = PageRequest.of(request.getPage(), 10, Sort.by(Sort.Order.asc("createAt")));

        Page<Customer> customer = customerRepository.findAllByOrderByCreateAtAsc(pageable);

        if (Objects.isNull(customer)) {
            throw new ResponseStatusException(HttpStatus.NO_CONTENT, "Customer content does not exist!");
        }

        List<CustomerResponse> customerResponses = customer.getContent().stream().map(this::toCustomerResponse).collect(Collectors.toList());

        return new PageImpl<>(customerResponses, pageable, customer.getTotalElements());
    }


    @Transactional(readOnly = true)
    public Page<CustomerResponse> listCustomerPageableDynamic(PageableCustomerRequest request) {

        Specification<Customer> specification = (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (Objects.nonNull(request.getSortField())) {
                predicates.add(criteriaBuilder.or(
                        criteriaBuilder.like(root.get("name"), "%" + request.getSortField() + "%")
                ));
            }

            return query.where(predicates.toArray(new Predicate[]{})).getRestriction();
        };

            PageRequest pageable = PageRequest.of(request.getPage(), request.getSize(), Sort.by(Sort.Order.asc("createAt")));
            Page<Customer> customer = customerRepository.findAll(specification, pageable);

            if (Objects.isNull(customer)) {
                throw new ResponseStatusException(HttpStatus.NO_CONTENT, "Customer content does not exist!");
            }

            List<CustomerResponse> customerResponses = customer.getContent().stream().map(this::toCustomerResponse).collect(Collectors.toList());

            return new PageImpl<>(customerResponses, pageable, customer.getTotalElements());

    }

    private CustomerResponse toCustomerResponse(Customer customer) {
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
