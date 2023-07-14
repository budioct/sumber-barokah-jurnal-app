package com.sumber.barokah.jurnal.dto.master;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UpdateProductRequest {

    @JsonIgnore
    @NotBlank
    private String categoryId;

    @JsonIgnore
    @NotBlank
    private String productId;

    @Size(max = 100)
    private String productCode;

    @NotBlank
    @Size(max = 100)
    private String name;

    private Integer quantity;

    private Integer minimumLimit;

    private Integer unit;

    private Long averagePrice;

    private Long lastPurchasePrice;

    private Long purchasePrice;

    private Long sellingPrice;

    @Size(max = 100)
    private String itemType;

    @Size(max = 255)
    private String description;

}
