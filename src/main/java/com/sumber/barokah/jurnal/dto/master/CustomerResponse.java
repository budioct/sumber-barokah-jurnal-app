package com.sumber.barokah.jurnal.dto.master;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CustomerResponse {

    private String customerId;

    private String name;

    private String company;

    private Long saldo;

    private String noHPhone;

    private String email;

    private String address;

    private Instant createAt;

    private Instant updateModifiedAt;

}
