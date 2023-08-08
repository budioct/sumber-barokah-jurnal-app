package com.sumber.barokah.jurnal.dto.transaksi;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

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

}
