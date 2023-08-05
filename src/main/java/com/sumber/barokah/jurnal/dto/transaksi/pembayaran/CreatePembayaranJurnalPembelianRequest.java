package com.sumber.barokah.jurnal.dto.transaksi.pembayaran;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CreatePembayaranJurnalPembelianRequest {

    //private String pembayaranId;

    @NotBlank
    @Size(max = 100)
    private String supplierId;

    @NotBlank
    @Size(max = 100)
    private String jurnalPembelianId;

    private LocalDateTime tanggalPembayaran;

    @NotNull
    private Long totalPembayaran;

    @Size(max = 100)
    private String status;

    @Size(max = 255)
    private String keterangan;

}
