package com.sumber.barokah.jurnal.entity.master.s_users;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "s_permissions")
public class Permissions {

    @Id
    @Column(name = "id")
    private String permissionsId;

    @Column(name = "permission_label")
    private String permissionLabel;

    @Column(name = "permission_value")
    private String permissionValue;

    @ManyToMany(mappedBy = "permissions", fetch = FetchType.EAGER)
    private List<Roles> roles;

}
