package com.sumber.barokah.jurnal.controller.transaksi;

import com.sumber.barokah.jurnal.dto.PagingResponse;
import com.sumber.barokah.jurnal.dto.WebResponse;
import com.sumber.barokah.jurnal.dto.master.PageableRequest;
import com.sumber.barokah.jurnal.dto.transaksi.CreateJurnalPenjualanRequest;
import com.sumber.barokah.jurnal.dto.transaksi.JurnalPenjualanResponse;
import com.sumber.barokah.jurnal.dto.transaksi.UpdateJurnalPenjualanRequest;
import com.sumber.barokah.jurnal.service.JurnalPenjualanService;
import com.sumber.barokah.jurnal.utilities.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.print.attribute.standard.Media;
import java.util.List;

@RestController
public class JurnalPenjualanController {

    @Autowired
    JurnalPenjualanService jurnalPenjualanService;

    @PostMapping(
            path = "/api/sb/jurnalpenjualans",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public WebResponse<JurnalPenjualanResponse> create(@RequestBody CreateJurnalPenjualanRequest request) {

        JurnalPenjualanResponse jurnalPenjualanResponse = jurnalPenjualanService.create(request);

        return WebResponse.<JurnalPenjualanResponse>builder()
                .data(jurnalPenjualanResponse)
                .status(HttpStatus.OK)
                .status_code(Constants.OK)
                .message(Constants.CREATE_MESSAGE)
                .build();

    }

    @GetMapping(
            path = "/api/sb/jurnalpenjualans",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public WebResponse<List<JurnalPenjualanResponse>> list() {

        List<JurnalPenjualanResponse> jurnalPenjualanResponses = jurnalPenjualanService.listJurnalPenjualan();

        return WebResponse.<List<JurnalPenjualanResponse>>builder()
                .data(jurnalPenjualanResponses)
                .status(HttpStatus.OK)
                .status_code(Constants.OK)
                .message(Constants.ITEM_EXIST_MESSAGE)
                .build();

    }

    @GetMapping(
            path = "/api/sb/jurnalpenjualans1",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public WebResponse<List<JurnalPenjualanResponse>> listPageable(@RequestParam(name = "sortField", required = false) String sortField,
                                                                   @RequestParam(name = "page", required = false, defaultValue = "0") Integer page,
                                                                   @RequestParam(name = "size", required = false, defaultValue = "10") Integer size

    ) {

        PageableRequest request = new PageableRequest();
        request.setPage(page);
        request.setSize(size);
        request.setSortField(sortField);

        Page<JurnalPenjualanResponse> jurnalPenjualanResponses = jurnalPenjualanService.listPageable(request);

        return WebResponse.<List<JurnalPenjualanResponse>>builder()
                .data(jurnalPenjualanResponses.getContent())
                .paging(PagingResponse.builder()
                        .currentPage(jurnalPenjualanResponses.getNumber())
                        .totalPage(jurnalPenjualanResponses.getTotalPages())
                        .size(jurnalPenjualanResponses.getSize())
                        .build())
                .status(HttpStatus.OK)
                .status_code(Constants.OK)
                .message(Constants.ITEM_EXIST_MESSAGE)
                .build();

    }

    @GetMapping(
            path = "/api/sb/{id}/jurnalpenjualans",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public WebResponse<JurnalPenjualanResponse> get(@PathVariable(name = "id") String id) {

        JurnalPenjualanResponse jurnalPenjualanResponse = jurnalPenjualanService.get(id);

        return WebResponse.<JurnalPenjualanResponse>builder()
                .data(jurnalPenjualanResponse)
                .status(HttpStatus.OK)
                .status_code(Constants.OK)
                .message(Constants.ITEM_EXIST_MESSAGE)
                .build();

    }

    @PutMapping(
            path = "/api/sb/{id}/jurnalpenjualans",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public WebResponse<JurnalPenjualanResponse> update(@RequestBody UpdateJurnalPenjualanRequest request,
                                                       @PathVariable(name = "id") String id) {

        request.setJurnalPenjualanId(id);
        JurnalPenjualanResponse jurnalPenjualanResponse = jurnalPenjualanService.update(request);

        return WebResponse.<JurnalPenjualanResponse>builder()
                .data(jurnalPenjualanResponse)
                .status(HttpStatus.OK)
                .status_code(Constants.OK)
                .message(Constants.UPDATE_MESSAGE)
                .build();

    }


}
