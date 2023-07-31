package com.sumber.barokah.jurnal.service;

import com.sumber.barokah.jurnal.dto.master.PageableRequest;
import com.sumber.barokah.jurnal.dto.transaksi.CreateJurnalPenjualanRequest;
import com.sumber.barokah.jurnal.dto.transaksi.JurnalPenjualanResponse;
import com.sumber.barokah.jurnal.dto.transaksi.UpdateJurnalPenjualanRequest;
import com.sumber.barokah.jurnal.entity.master.Customer;
import org.springframework.data.domain.Page;

import java.util.List;

public interface JurnalPenjualanService {

    JurnalPenjualanResponse create(CreateJurnalPenjualanRequest request);

    List<JurnalPenjualanResponse> listJurnalPenjualan();

    Page<JurnalPenjualanResponse> listPageable(PageableRequest request);

    JurnalPenjualanResponse get(String id);

    JurnalPenjualanResponse update(UpdateJurnalPenjualanRequest request);

}
