package com.sumber.barokah.jurnal.dto.transaksi;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PembayaranResponse {

    private String pembayaranId;

    private LocalDateTime tanggalPembayaran;

    private Long totalPembayaran;

    private String status;

    private String keterangan;

    private LocalDateTime createAt;

    private LocalDateTime updateModifiedAt;

    // return bukan entity tetapi dari dto nya supaya tidak error runtime exception: Could not write JSON: Infinite recursion (StackOverflowError)] with root cause
    private JurnalPembelianResponse jurnalPembeliansLikeBy;

    // infinite recurtion loop
    //private JurnalPembelian jurnalPembeliansLikeBy;

}
