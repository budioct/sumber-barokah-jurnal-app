package com.sumber.barokah.jurnal.service;

import com.sumber.barokah.jurnal.dto.transaksi.CreateJurnalPembelianRequest;
import com.sumber.barokah.jurnal.dto.transaksi.JurnalPembelianResponse;

import java.util.List;

public interface JurnalPembelianService {

    JurnalPembelianResponse create(CreateJurnalPembelianRequest request);

    List<JurnalPembelianResponse> listJurnalPembelian();

}
