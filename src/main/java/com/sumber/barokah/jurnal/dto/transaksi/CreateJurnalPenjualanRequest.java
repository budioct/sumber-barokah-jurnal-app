package com.sumber.barokah.jurnal.dto.transaksi;

import com.sumber.barokah.jurnal.dto.transaksi.jurnalpembelian.CreateProductJurnalPembelianRequest;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CreateJurnalPenjualanRequest {

    @NotBlank
    @Size(max = 100)
    private String customerId;

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
