package com.sumber.barokah.jurnal.entity.master.s_users;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "s_users")
public class Users {

    @Id
    @Column(name = "id")
    private String userId;

    @Column(name = "username")
    private String username;

    @Column(name = "password")
    private String password;

    @Column(name = "active")
    private boolean active;


    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "s_users_roles",
            joinColumns = @JoinColumn(name = "id_user", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "id_role", referencedColumnName = "id")
    )
    private List<Roles> roles;


}
