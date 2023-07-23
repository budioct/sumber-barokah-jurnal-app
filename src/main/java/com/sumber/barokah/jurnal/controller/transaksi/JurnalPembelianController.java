package com.sumber.barokah.jurnal.controller.transaksi;

import com.sumber.barokah.jurnal.dto.PagingResponse;
import com.sumber.barokah.jurnal.dto.WebResponse;
import com.sumber.barokah.jurnal.dto.master.PageableRequest;
import com.sumber.barokah.jurnal.dto.transaksi.CreateJurnalPembelianRequest;
import com.sumber.barokah.jurnal.dto.transaksi.JurnalPembelianResponse;
import com.sumber.barokah.jurnal.dto.transaksi.UpdateJurnalPembelianRequest;
import com.sumber.barokah.jurnal.service.JurnalPembelianService;
import com.sumber.barokah.jurnal.utilities.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class JurnalPembelianController {

    @Autowired
    JurnalPembelianService jurnalPembelianService;

    @PostMapping(
            path = "/api/sb/jurnalpembelians",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public WebResponse<JurnalPembelianResponse> create(@RequestBody CreateJurnalPembelianRequest request) {

        JurnalPembelianResponse jurnalPembelianResponse = jurnalPembelianService.create(request);

        return WebResponse.<JurnalPembelianResponse>builder()
                .data(jurnalPembelianResponse)
                .status(HttpStatus.OK)
                .status_code(Constants.OK)
                .message(Constants.CREATE_MESSAGE)
                .build();
    }

    @GetMapping(
            path = "/api/sb/jurnalpembelians",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public WebResponse<List<JurnalPembelianResponse>> list() {

        List<JurnalPembelianResponse> jurnalPembelianResponses = jurnalPembelianService.listJurnalPembelian();

        return WebResponse.<List<JurnalPembelianResponse>>builder()
                .data(jurnalPembelianResponses)
                .status(HttpStatus.OK)
                .status_code(Constants.OK)
                .message(Constants.ITEM_EXIST_MESSAGE)
                .build();
    }

    @GetMapping(
            path = "/api/sb/{id}/jurnalpembelians",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public WebResponse<JurnalPembelianResponse> get(@PathVariable(name = "id") String id) {

        JurnalPembelianResponse jurnalPembelianResponse = jurnalPembelianService.get(id);

        return WebResponse.<JurnalPembelianResponse>builder()
                .data(jurnalPembelianResponse)
                .status(HttpStatus.OK)
                .status_code(Constants.OK)
                .message(Constants.ITEM_EXIST_MESSAGE)
                .build();

    }

    @PutMapping(
            path = "/api/sb/{id}/jurnalpembelians",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public WebResponse<JurnalPembelianResponse> update(@RequestBody UpdateJurnalPembelianRequest request,
                                                       @PathVariable(name = "id") String id) {

        request.setJurnalPembelianId(id);
        JurnalPembelianResponse jurnalPembelianResponse = jurnalPembelianService.update(request);

        return WebResponse.<JurnalPembelianResponse>builder()
                .data(jurnalPembelianResponse)
                .status(HttpStatus.OK)
                .status_code(Constants.OK)
                .message(Constants.UPDATE_MESSAGE)
                .build();
    }

    @DeleteMapping(
            path = "/api/sb/{id}/jurnalpembelians",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public WebResponse<String> delete(@PathVariable(name = "id") String id) {

        jurnalPembelianService.delete(id);

        return WebResponse.<String>builder()
                .data("")
                .status(HttpStatus.OK)
                .status_code(Constants.OK)
                .message(Constants.DELETE_MESSAGE)
                .build();

    }

    @GetMapping(
            path = "/api/sb/jurnalpembelians1",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public WebResponse<List<JurnalPembelianResponse>> listPageable(@RequestParam(name = "sortField", required = false) String sortField,
                                                                   @RequestParam(name = "page", required = false, defaultValue = "0") Integer page,
                                                                   @RequestParam(name = "size", required = false, defaultValue = "10") Integer size
    ) {

        PageableRequest request = new PageableRequest();
        request.setPage(page);
        request.setSize(size);
        request.setSortField(sortField);

        Page<JurnalPembelianResponse> jurnalPembelianResponses = jurnalPembelianService.listPageable(request);

        return WebResponse.<List<JurnalPembelianResponse>>builder()
                .data(jurnalPembelianResponses.getContent())
                .paging(PagingResponse.builder()
                        .currentPage(jurnalPembelianResponses.getNumber())
                        .totalPage(jurnalPembelianResponses.getTotalPages())
                        .size(jurnalPembelianResponses.getSize())
                        .build())
                .status(HttpStatus.OK)
                .status_code(Constants.OK)
                .message(Constants.ITEM_EXIST_MESSAGE)
                .build();
    }


}
