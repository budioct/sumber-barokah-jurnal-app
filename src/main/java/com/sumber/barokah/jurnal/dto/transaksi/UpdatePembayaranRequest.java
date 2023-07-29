package com.sumber.barokah.jurnal.dto.transaksi;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
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
public class UpdatePembayaranRequest {

    @JsonIgnore
    @NotBlank
    private String pembayaranId;

    @NotBlank
    private String jurnalPembelianId;

    private LocalDateTime tanggalPembayaran;

    private Long nominalPembayaran;

    @Size(max = 100)
    private String status;

    @Size(max = 255)
    private String keterangan;

}
