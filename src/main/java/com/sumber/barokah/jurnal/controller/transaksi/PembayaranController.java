package com.sumber.barokah.jurnal.controller.transaksi;

import com.sumber.barokah.jurnal.dto.WebResponse;
import com.sumber.barokah.jurnal.dto.transaksi.CreatePembayaranRequest;
import com.sumber.barokah.jurnal.dto.transaksi.PembayaranResponse;
import com.sumber.barokah.jurnal.service.PembayaranService;
import com.sumber.barokah.jurnal.utilities.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

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
    public WebResponse<List<PembayaranResponse>> list(){

        List<PembayaranResponse> pembayaranResponses = pembayaranService.list();

        return WebResponse.<List<PembayaranResponse>>builder()
                .data(pembayaranResponses)
                .status(HttpStatus.OK)
                .status_code(Constants.OK)
                .message(Constants.ITEM_EXIST_MESSAGE)
                .build();

    }

}
