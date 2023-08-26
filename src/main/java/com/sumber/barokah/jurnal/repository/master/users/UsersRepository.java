package com.sumber.barokah.jurnal.repository.master.users;

import com.sumber.barokah.jurnal.entity.master.s_users.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UsersRepository extends JpaRepository<Users, String> {

    Optional<Users> findFirstByUserId(String id);

    Optional<Users> findFirstByUsername(String username);

}
