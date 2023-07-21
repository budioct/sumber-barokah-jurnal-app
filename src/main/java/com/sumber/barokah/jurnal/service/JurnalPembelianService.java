package com.sumber.barokah.jurnal.service;

import com.sumber.barokah.jurnal.dto.transaksi.CreateJurnalPembelianRequest;
import com.sumber.barokah.jurnal.dto.transaksi.JurnalPembelianResponse;
import com.sumber.barokah.jurnal.dto.transaksi.UpdateJurnalPembelianRequest;

import java.util.List;

public interface JurnalPembelianService {

    JurnalPembelianResponse create(CreateJurnalPembelianRequest request);

    List<JurnalPembelianResponse> listJurnalPembelian();

    JurnalPembelianResponse get(String id);

    JurnalPembelianResponse update(UpdateJurnalPembelianRequest request);

    void delete(String id);

}
