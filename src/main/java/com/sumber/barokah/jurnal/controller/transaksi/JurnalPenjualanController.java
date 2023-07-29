package com.sumber.barokah.jurnal.controller.transaksi;

import com.sumber.barokah.jurnal.dto.WebResponse;
import com.sumber.barokah.jurnal.dto.transaksi.CreateJurnalPenjualanRequest;
import com.sumber.barokah.jurnal.dto.transaksi.JurnalPenjualanResponse;
import com.sumber.barokah.jurnal.service.JurnalPenjualanService;
import com.sumber.barokah.jurnal.utilities.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class JurnalPenjualanController {

    @Autowired
    JurnalPenjualanService jurnalPenjualanService;

    @PostMapping(
            path = "/api/sb/jurnalpenjualans",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public WebResponse<JurnalPenjualanResponse> create(@RequestBody CreateJurnalPenjualanRequest request){

        JurnalPenjualanResponse jurnalPenjualanResponse = jurnalPenjualanService.create(request);

        return WebResponse.<JurnalPenjualanResponse>builder()
                .data(jurnalPenjualanResponse)
                .status(HttpStatus.OK)
                .status_code(Constants.OK)
                .message(Constants.CREATE_MESSAGE)
                .build();

    }


}
