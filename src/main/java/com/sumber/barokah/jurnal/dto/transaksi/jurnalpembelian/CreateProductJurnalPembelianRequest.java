package com.sumber.barokah.jurnal.dto.transaksi.jurnalpembelian;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreateProductJurnalPembelianRequest {

    @NotBlank
    private String productId;

    @JsonIgnore
    private String categoryId;

    @Size(max = 100)
    private String productCode;

    @Size(max = 100)
    private String name;

    private Integer quantity;

    private Integer minimumLimit;

    private String unit;

    private Long averagePrice;

    private Long lastPurchasePrice;

    private Long purchasePrice;

    private Long sellingPrice;

    @Size(max = 100)
    private String itemType;

    @Size(max = 255)
    private String description;

}
