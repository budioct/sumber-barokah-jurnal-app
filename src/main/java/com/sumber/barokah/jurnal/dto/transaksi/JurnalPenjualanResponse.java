package com.sumber.barokah.jurnal.dto.transaksi;

import com.sumber.barokah.jurnal.dto.master.ProductResponse;
import com.sumber.barokah.jurnal.entity.master.Customer;
import com.sumber.barokah.jurnal.entity.master.Supplier;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class JurnalPenjualanResponse {

    private String jurnalPenjualanId;

    private String noFaktur;

    private LocalDateTime tanggalTransaksi;

    private LocalDateTime tanggalJatuhTempo;

    private String status;

    private Long sisaTagihan;

    private Long jumlahTotal;

    private String noTransaksi;

    private String tags;

    private Instant createAt;

    private Instant updateModifiedAt;

    private Customer customer; // ke depanya di ganti DTO

    //@JsonIgnore
    private List<ProductResponse> products;

    //private List<Pembayaran> likes0;

}
