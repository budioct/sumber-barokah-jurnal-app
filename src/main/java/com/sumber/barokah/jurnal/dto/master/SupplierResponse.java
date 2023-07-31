package com.sumber.barokah.jurnal.dto.master;

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
public class SupplierResponse {

    private String supplierId;

    private String name;

    private String company;

    private Long saldo;

    private String noHPhone;

    private String email;

    private String address;

    private LocalDateTime createAt;

    private LocalDateTime updateModifiedAt;

}
