package com.sumber.barokah.jurnal.dto.transaksi;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sumber.barokah.jurnal.dto.master.ProductResponse;
import com.sumber.barokah.jurnal.entity.master.Product;
import com.sumber.barokah.jurnal.entity.master.Supplier;
import com.sumber.barokah.jurnal.entity.transaksi.Pembayaran;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class JurnalPembelianResponse {

    private String jurnalPembelianId;

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

    private Supplier supplier;

    //@JsonIgnore
    private List<ProductResponse> products;

    //private List<Pembayaran> likes0;

}
