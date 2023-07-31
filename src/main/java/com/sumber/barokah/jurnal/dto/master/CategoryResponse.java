package com.sumber.barokah.jurnal.dto.master;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sumber.barokah.jurnal.entity.master.Product;
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
public class CategoryResponse {

    private String categoryId;

    private String name;

    //@JsonIgnore
    //private List<Product> products;

    private LocalDateTime createAt;

    private LocalDateTime updateModifiedAt;

}
