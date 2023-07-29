package com.sumber.barokah.jurnal.controller.transaksi;

import com.sumber.barokah.jurnal.dto.PagingResponse;
import com.sumber.barokah.jurnal.dto.WebResponse;
import com.sumber.barokah.jurnal.dto.master.PageableRequest;
import com.sumber.barokah.jurnal.dto.transaksi.CreatePembayaranRequest;
import com.sumber.barokah.jurnal.dto.transaksi.PembayaranResponse;
import com.sumber.barokah.jurnal.dto.transaksi.UpdatePembayaranRequest;
import com.sumber.barokah.jurnal.service.PembayaranService;
import com.sumber.barokah.jurnal.utilities.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class PembayaranController {

    @Autowired
    private PembayaranService pembayaranService;

    @PostMapping(
            path = "/api/sb/pembayaran",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public WebResponse<PembayaranResponse> create(@RequestBody CreatePembayaranRequest request) {

        PembayaranResponse pembayaranResponse = pembayaranService.create(request);

        return WebResponse.<PembayaranResponse>builder()
                .data(pembayaranResponse)
                .status(HttpStatus.OK)
                .status_code(Constants.OK)
                .message(Constants.CREATE_MESSAGE)
                .build();

    }

    @GetMapping(
            path = "/api/sb/pembayaran",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public WebResponse<List<PembayaranResponse>> list() {

        List<PembayaranResponse> pembayaranResponses = pembayaranService.list();

        return WebResponse.<List<PembayaranResponse>>builder()
                .data(pembayaranResponses)
                .status(HttpStatus.OK)
                .status_code(Constants.OK)
                .message(Constants.ITEM_EXIST_MESSAGE)
                .build();

    }

    @GetMapping(
            path = "/api/sb/pembayaran1",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public WebResponse<List<PembayaranResponse>> listPageable(@RequestParam(name = "sortField", required = false) String sortField,
                                                              @RequestParam(name = "page", required = false, defaultValue = "0") Integer page,
                                                              @RequestParam(name = "size", required = false, defaultValue = "10") Integer size

    ) {

        PageableRequest request = new PageableRequest();
        request.setPage(page);
        request.setSize(size);
        request.setSortField(sortField);

        Page<PembayaranResponse> pembayaranResponses = pembayaranService.listPageable(request);

        return WebResponse.<List<PembayaranResponse>>builder()
                .data(pembayaranResponses.getContent())
                .paging(PagingResponse.builder()
                        .currentPage(pembayaranResponses.getNumber())
                        .totalPage(pembayaranResponses.getTotalPages())
                        .size(pembayaranResponses.getSize())
                        .build())
                .status(HttpStatus.OK)
                .status_code(Constants.OK)
                .message(Constants.ITEM_EXIST_MESSAGE)
                .build();

    }

    @GetMapping(
            path = "/api/sb/{id}/pembayaran",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public WebResponse<PembayaranResponse> get(@PathVariable(name = "id") String id) {

        PembayaranResponse pembayaranResponse = pembayaranService.get(id);

        return WebResponse.<PembayaranResponse>builder()
                .data(pembayaranResponse)
                .status(HttpStatus.OK)
                .status_code(Constants.OK)
                .message(Constants.ITEM_EXIST_MESSAGE)
                .build();

    }

    @PutMapping(
            path = "/api/sb/{id}/pembayaran",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public  WebResponse<PembayaranResponse> update(@PathVariable(name = "id") String id,
                                                   @RequestBody UpdatePembayaranRequest request){

        request.setPembayaranId(id);
        PembayaranResponse pembayaranResponse = pembayaranService.update(request);

        return WebResponse.<PembayaranResponse>builder()
                .data(pembayaranResponse)
                .status(HttpStatus.OK)
                .status_code(Constants.OK)
                .message(Constants.UPDATE_MESSAGE)
                .build();

    }

    @DeleteMapping(
            path = "/api/sb/{id}/pembayaran",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public WebResponse<String> delete(@PathVariable(name = "id") String id){

        pembayaranService.delete(id);

        return WebResponse.<String>builder()
                .data("")
                .status(HttpStatus.OK)
                .status_code(Constants.OK)
                .message(Constants.DELETE_MESSAGE)
                .build();

    }


}
