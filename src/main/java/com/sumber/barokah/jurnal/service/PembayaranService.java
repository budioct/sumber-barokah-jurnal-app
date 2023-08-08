package com.sumber.barokah.jurnal.service;

import com.sumber.barokah.jurnal.dto.master.PageableRequest;
import com.sumber.barokah.jurnal.dto.transaksi.*;
import com.sumber.barokah.jurnal.dto.transaksi.pembayaran.CreatePembayaranJurnalPembelianRequest;
import com.sumber.barokah.jurnal.dto.transaksi.pembayaran.CreatePembayaranJurnalPenjualanRequest;
import org.springframework.data.domain.Page;

import java.util.List;

public interface PembayaranService {

    PembayaranResponse create(CreatePembayaranRequest request);

    PembayaranJurnalPembelianResponse createPembayaranJurnalPembelian(CreatePembayaranJurnalPembelianRequest request);

    PembayaranJurnalPenjualanResponse createPembayaranJurnalPenjualan(CreatePembayaranJurnalPenjualanRequest request);

    List<PembayaranResponse> list();

    Page<PembayaranResponse> listPageable(PageableRequest request);

    PembayaranResponse get(String id);

    PembayaranResponse update(UpdatePembayaranRequest request);

    void delete(String id);

}
