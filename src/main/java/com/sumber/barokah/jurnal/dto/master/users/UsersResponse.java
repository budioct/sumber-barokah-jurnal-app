package com.sumber.barokah.jurnal.dto.master.users;

import jakarta.persistence.Column;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UsersResponse {

    private String userId;

    private String username;

    private String password;

    private boolean active;

    private List<RolesResponse> roles;

}
