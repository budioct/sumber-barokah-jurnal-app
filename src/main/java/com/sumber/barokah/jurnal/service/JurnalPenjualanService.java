package com.sumber.barokah.jurnal.service;

import com.sumber.barokah.jurnal.dto.transaksi.CreateJurnalPenjualanRequest;
import com.sumber.barokah.jurnal.dto.transaksi.JurnalPenjualanResponse;

public interface JurnalPenjualanService {

    JurnalPenjualanResponse create(CreateJurnalPenjualanRequest request);

}
