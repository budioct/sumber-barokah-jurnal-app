package com.sumber.barokah.jurnal.entity.master.s_users;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "s_roles")
public class Roles {

    @Id
    @Column(name = "id")
    private String roleId;

    @Column(name = "name")
    private String roleName;

    @ManyToMany(mappedBy = "roles" ,fetch = FetchType.EAGER)
    private List<Users> user;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "s_roles_permissions",
            joinColumns = @JoinColumn(name = "id_role", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "id_permission", referencedColumnName = "id")
    )
    private List<Permissions> permissions;

}
