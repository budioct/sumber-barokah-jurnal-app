package com.sumber.barokah.jurnal.service;

import com.sumber.barokah.jurnal.dto.master.PageableRequest;
import com.sumber.barokah.jurnal.dto.transaksi.CreatePembayaranRequest;
import com.sumber.barokah.jurnal.dto.transaksi.PembayaranResponse;
import org.springframework.data.domain.Page;

import java.util.List;

public interface PembayaranService {

    PembayaranResponse create(CreatePembayaranRequest request);

    List<PembayaranResponse> list();

    Page<PembayaranResponse> listPageable(PageableRequest request);

    PembayaranResponse get(String id);

}
