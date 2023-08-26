package com.sumber.barokah.jurnal.dto.master.users;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UpdateUsersRequest {

    @JsonIgnore
    private String userId;

    @NotBlank
    private String username;

    @NotBlank
    private String password;

    @NotBlank
    private boolean active;

}
