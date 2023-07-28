package com.sumber.barokah.jurnal.service;

import com.sumber.barokah.jurnal.dto.transaksi.CreatePembayaranRequest;
import com.sumber.barokah.jurnal.dto.transaksi.PembayaranResponse;

import java.util.List;

public interface PembayaranService {

    PembayaranResponse create(CreatePembayaranRequest request);

    List<PembayaranResponse> list();

}
