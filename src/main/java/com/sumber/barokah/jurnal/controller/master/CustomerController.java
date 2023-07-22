package com.sumber.barokah.jurnal.controller.master;

import com.sumber.barokah.jurnal.dto.PagingResponse;
import com.sumber.barokah.jurnal.dto.WebResponse;
import com.sumber.barokah.jurnal.dto.master.CreateCustomerRequest;
import com.sumber.barokah.jurnal.dto.master.CustomerResponse;
import com.sumber.barokah.jurnal.dto.master.PageableRequest;
import com.sumber.barokah.jurnal.dto.master.UpdateCustomerRequest;
import com.sumber.barokah.jurnal.service.CustomerService;
import com.sumber.barokah.jurnal.utilities.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
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

        return WebResponse.<CustomerResponse>builder()
                .data(customerResponse)
                .status(HttpStatus.OK)
                .status_code(Constants.OK)
                .message(Constants.CREATE_MESSAGE)
                .build();
    }

    @GetMapping(
            path = "/api/sb/customers",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public WebResponse<List<CustomerResponse>> getList() {

        List<CustomerResponse> customerResponses = customerService.listCustomer();

        return WebResponse.<List<CustomerResponse>>builder()
                .data(customerResponses)
                .status(HttpStatus.OK)
                .status_code(Constants.OK)
                .message(Constants.ITEM_EXIST_MESSAGE)
                .build();
    }

    @GetMapping(
            path = "/api/sb/{id}/customers",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public WebResponse<CustomerResponse> get(@PathVariable(name = "id") String id) {

        CustomerResponse customerResponse = customerService.get(id);

        return WebResponse.<CustomerResponse>builder()
                .data(customerResponse)
                .status(HttpStatus.OK)
                .status_code(Constants.OK)
                .message(Constants.ITEM_EXIST_MESSAGE)
                .build();

    }

    @PutMapping(
            path = "/api/sb/{id}/customers",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public WebResponse<CustomerResponse> update(@RequestBody UpdateCustomerRequest request,
                                                @PathVariable(name = "id") String id) {

        request.setCustomerId(id);
        CustomerResponse customerResponse = customerService.update(request);

        return WebResponse.<CustomerResponse>builder()
                .data(customerResponse)
                .status(HttpStatus.OK)
                .status_code(Constants.OK)
                .message(Constants.UPDATE_MESSAGE)
                .build();

    }

    @DeleteMapping(
            path = "/api/sb/{id}/customers",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public WebResponse<String> delete(@PathVariable(name = "id") String id) {

        customerService.delete(id);

        return WebResponse.<String>builder()
                .data("")
                .status(HttpStatus.OK)
                .status_code(Constants.OK)
                .message(Constants.DELETE_MESSAGE)
                .build();

    }


    @GetMapping(
            path = "/api/sb/customers/dynamic",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public WebResponse<List<CustomerResponse>> listPageable(@RequestParam(name = "sortField", required = false) String sortField,
                                                            @RequestParam(name = "page", required = false, defaultValue = "0") Integer page,
                                                            @RequestParam(name = "size", required = false, defaultValue = "10") Integer size
    ) {
        PageableRequest request = new PageableRequest();
        request.setPage(page);
        request.setSize(size);
        request.setSortField(sortField);

        Page<CustomerResponse> customerResponses = customerService.listCustomerPageableDynamic(request);

        return WebResponse.<List<CustomerResponse>>builder()
                .data(customerResponses.getContent())
                .paging(PagingResponse.builder()
                        .currentPage(customerResponses.getNumber())
                        .totalPage(customerResponses.getTotalPages())
                        .size(customerResponses.getSize())
                        .build())
                .build();

    }

    @GetMapping(
            path = "/api/sb/customers/static",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public WebResponse<List<CustomerResponse>> listPageable1(@RequestParam(name = "page", required = false, defaultValue = "0") Integer page) {

        PageableRequest request = new PageableRequest();
        request.setPage(page);

        Page<CustomerResponse> customerResponses = customerService.listCustomerPageableStatic(request);

        return WebResponse.<List<CustomerResponse>>builder()
                .data(customerResponses.getContent())
                .paging(PagingResponse.builder()
                        .currentPage(customerResponses.getNumber())
                        .totalPage(customerResponses.getTotalPages())
                        .size(customerResponses.getSize())
                        .build())
                .build();

    }


}
