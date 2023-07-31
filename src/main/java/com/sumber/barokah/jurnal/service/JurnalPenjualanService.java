package com.sumber.barokah.jurnal.service;

import com.sumber.barokah.jurnal.dto.transaksi.CreateJurnalPenjualanRequest;
import com.sumber.barokah.jurnal.dto.transaksi.JurnalPenjualanResponse;

import java.util.List;

public interface JurnalPenjualanService {

    JurnalPenjualanResponse create(CreateJurnalPenjualanRequest request);

    List<JurnalPenjualanResponse> listJurnalPenjualan();

}
