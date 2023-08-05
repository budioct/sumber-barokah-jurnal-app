package com.sumber.barokah.jurnal.service;

import com.sumber.barokah.jurnal.dto.master.PageableRequest;
import com.sumber.barokah.jurnal.dto.transaksi.CreatePembayaranRequest;
import com.sumber.barokah.jurnal.dto.transaksi.PembayaranResponse;
import com.sumber.barokah.jurnal.dto.transaksi.UpdatePembayaranRequest;
import com.sumber.barokah.jurnal.dto.transaksi.pembayaran.CreatePembayaranJurnalPembelianRequest;
import org.springframework.data.domain.Page;

import java.util.List;

public interface PembayaranService {

    PembayaranResponse create(CreatePembayaranRequest request);

    PembayaranResponse createPembayaran(CreatePembayaranJurnalPembelianRequest request);

    List<PembayaranResponse> list();

    Page<PembayaranResponse> listPageable(PageableRequest request);

    PembayaranResponse get(String id);

    PembayaranResponse update(UpdatePembayaranRequest request);

    void delete(String id);

}
