package com.sumber.barokah.jurnal.service;

import com.sumber.barokah.jurnal.dto.transaksi.CreatePembayaranRequest;
import com.sumber.barokah.jurnal.dto.transaksi.PembayaranResponse;

public interface PembayaranService {

    PembayaranResponse create(CreatePembayaranRequest request);

}
