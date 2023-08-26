package com.sumber.barokah.jurnal.dto.master.users;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CreateUsersRequest {

    @NotBlank
    private String username;

    @NotBlank
    private String password;

}
