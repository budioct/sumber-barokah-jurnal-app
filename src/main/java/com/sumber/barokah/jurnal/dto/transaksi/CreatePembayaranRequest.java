package com.sumber.barokah.jurnal.dto.transaksi;

import com.sumber.barokah.jurnal.dto.transaksi.pembayaran.CreatePembayaranJurnalPembelianRequest;
import com.sumber.barokah.jurnal.entity.transaksi.JurnalPembelian;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
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
public class CreatePembayaranRequest {

//    @NotBlank
//    @Size(max = 100)
//    private String supplierId;

//    private List<CreatePembayaranJurnalPembelianRequest> createJurnalPembelians;

    private LocalDateTime tanggalPembayaran;

    @NotNull
    private Long totalPembayaran;

    @Size(max = 100)
    private String status;

    @Size(max = 255)
    private String keterangan;

}
