package com.sumber.barokah.jurnal.dto.transaksi;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PembayaranResponse {

    private String pembayaranId;

    private LocalDateTime tanggalPembayaran;

    private Long nominalPembayaran;

    private String status;

    private String keterangan;

    private Instant createAt;

    private Instant updateModifiedAt;

    // return bukan entity tetapi dari dto nya supaya tidak error runtime exception: Could not write JSON: Infinite recursion (StackOverflowError)] with root cause
    private JurnalPembelianResponse jurnalPembeliansLikeBy;

    //private JurnalPembelian jurnalPembeliansLikeBy;

}
