package com.sumber.barokah.jurnal.dto.transaksi;

import com.sumber.barokah.jurnal.entity.transaksi.JurnalPembelian;
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
public class CreatePembayaranRequest {

    //private String pembayaranId;

    //@NotBlank
    //@Size(max = 100)
    //private String jurnalPembelianId;

    private LocalDateTime tanggalPembayaran;

    private Long totalPembayaran;

    @Size(max = 100)
    private String status;

    @Size(max = 255)
    private String keterangan;

    //private Instant createAt;

    //private Instant updateModifiedAt;

}
