package com.sumber.barokah.jurnal.repository.master.users;

import com.sumber.barokah.jurnal.entity.master.s_users.Permissions;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PermissionsRepository extends JpaRepository<Permissions, String> {

    Optional<Permissions> findFirstByPermissionsId(String id);

}
