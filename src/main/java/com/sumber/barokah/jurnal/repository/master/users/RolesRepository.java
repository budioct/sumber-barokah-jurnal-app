package com.sumber.barokah.jurnal.repository.master.users;

import com.sumber.barokah.jurnal.entity.master.s_users.Roles;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RolesRepository extends JpaRepository<Roles, String> {

//    Optional<Roles> findFirstByRoleId(String id);

    Optional<Roles> findFirstByRoleId(String id);

}
