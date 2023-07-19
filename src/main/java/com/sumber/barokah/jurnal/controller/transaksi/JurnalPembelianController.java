package com.sumber.barokah.jurnal.controller.transaksi;

import com.sumber.barokah.jurnal.dto.WebResponse;
import com.sumber.barokah.jurnal.dto.transaksi.CreateJurnalPembelianRequest;
import com.sumber.barokah.jurnal.dto.transaksi.JurnalPembelianResponse;
import com.sumber.barokah.jurnal.service.JurnalPembelianService;
import com.sumber.barokah.jurnal.utilities.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class JurnalPembelianController {

    @Autowired
    JurnalPembelianService jurnalPembelianService;

    @PostMapping(
            path = "/api/sb/jurnalpembelians",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public WebResponse<JurnalPembelianResponse> create(@RequestBody CreateJurnalPembelianRequest request){

        JurnalPembelianResponse jurnalPembelianResponse = jurnalPembelianService.create(request);

        return WebResponse.<JurnalPembelianResponse>builder()
                .data(jurnalPembelianResponse)
                .status(HttpStatus.OK)
                .status_code(Constants.OK)
                .message(Constants.CREATE_MESSAGE)
                .build();
    }


}
