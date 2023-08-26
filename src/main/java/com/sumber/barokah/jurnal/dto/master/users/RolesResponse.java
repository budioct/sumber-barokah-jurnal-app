package com.sumber.barokah.jurnal.dto.master.users;

import com.sumber.barokah.jurnal.entity.master.s_users.Permissions;
import com.sumber.barokah.jurnal.entity.master.s_users.Users;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RolesResponse {

    private String roleId;

    private String roleName;

//    private List<PermissionsResponse> permissions;

}
