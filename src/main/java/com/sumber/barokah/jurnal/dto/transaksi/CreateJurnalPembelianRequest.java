package com.sumber.barokah.jurnal.dto.transaksi;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sumber.barokah.jurnal.dto.master.CreateProductRequest;
import com.sumber.barokah.jurnal.dto.transaksi.jurnalpembelian.CreateProductJurnalPembelianRequest;
import com.sumber.barokah.jurnal.entity.master.Product;
import com.sumber.barokah.jurnal.entity.master.Supplier;
import com.sumber.barokah.jurnal.entity.transaksi.Pembayaran;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CreateJurnalPembelianRequest {

    @NotBlank
    private String supplierId;

    //@JsonIgnore
    private List<CreateProductJurnalPembelianRequest> createProducts;

    //private List<Pembayaran> likes0;

    @Size(max = 100)
    private String noFaktur;

    private LocalDateTime tanggalTransaksi;

    private LocalDateTime tanggalJatuhTempo;

    @Size(max = 100)
    private String status;

    private Long sisaTagihan;

    private Long jumlahTotal;

    @Size(max = 100)
    private String noTransaksi;

    @Size(max = 100)
    private String tags;

}
