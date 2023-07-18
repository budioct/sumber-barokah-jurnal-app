package com.sumber.barokah.jurnal.dto.master;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sumber.barokah.jurnal.entity.master.Category;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductResponse {

    private String productId;

    private String productCode;

    private String name;

    private Integer quantity;

    private Integer minimumLimit;

    private String unit;

    private Long averagePrice;

    private Long lastPurchasePrice;

    private Long purchasePrice;

    private Long sellingPrice;

    private String itemType;

    private String description;

    private String categoryId;

    private Instant createAt;

    private Instant updateModifiedAt;

}
