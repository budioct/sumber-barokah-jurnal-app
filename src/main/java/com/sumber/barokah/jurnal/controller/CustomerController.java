package com.sumber.barokah.jurnal.controller;

import com.sumber.barokah.jurnal.dto.WebResponse;
import com.sumber.barokah.jurnal.dto.master.CreateCustomerRequest;
import com.sumber.barokah.jurnal.dto.master.CustomerResponse;
import com.sumber.barokah.jurnal.dto.master.UpdateCustomerRequest;
import com.sumber.barokah.jurnal.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class CustomerController {

    @Autowired
    private CustomerService customerService;

    @PostMapping(
            path = "/api/sb/customers",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public WebResponse<CustomerResponse> create(@RequestBody CreateCustomerRequest request) {

        CustomerResponse customerResponse = customerService.create(request);

        return WebResponse.<CustomerResponse>builder().data(customerResponse).build();
    }

    @GetMapping(
            path = "/api/sb/customers",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public WebResponse<List<CustomerResponse>> getList(){

        List<CustomerResponse> customerResponses = customerService.listCustomer();

        return WebResponse.<List<CustomerResponse>>builder().data(customerResponses).build();
    }

    @GetMapping(
            path = "/api/sb/{id}/customers",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public WebResponse<CustomerResponse> get(@PathVariable(name = "id") String id){

        CustomerResponse customerResponse = customerService.get(id);

        return WebResponse.<CustomerResponse>builder().data(customerResponse).build();

    }

    @PutMapping(
            path = "/api/sb/{id}/customers",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public WebResponse<CustomerResponse> update(@RequestBody UpdateCustomerRequest request,
                                                @PathVariable(name = "id") String id){

        request.setCustomerId(id);
        CustomerResponse customerResponse = customerService.update(request);

        return WebResponse.<CustomerResponse>builder().data(customerResponse).build();

    }


}
