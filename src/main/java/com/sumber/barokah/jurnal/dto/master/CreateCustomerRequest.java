package com.sumber.barokah.jurnal.dto.master;

import jakarta.persistence.Column;
import jakarta.validation.constraints.Email;
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
public class CreateCustomerRequest {

    @NotBlank
    @Size(max = 100)
    private String name;

    @Size(max = 100)
    private String company;

    private Long saldo;

    @NotBlank
    @Size(max = 20)
    private String noHPhone;

    @NotBlank
    @Email
    @Size(max = 100)
    private String email;

    @Size(max = 255)
    private String address;

}
