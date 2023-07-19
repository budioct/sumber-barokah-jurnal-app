package com.sumber.barokah.jurnal.service;

import com.sumber.barokah.jurnal.dto.transaksi.CreateJurnalPembelianRequest;
import com.sumber.barokah.jurnal.dto.transaksi.JurnalPembelianResponse;

public interface JurnalPembelianService {

    JurnalPembelianResponse create(CreateJurnalPembelianRequest request);

}
