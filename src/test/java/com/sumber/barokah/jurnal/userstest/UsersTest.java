package com.sumber.barokah.jurnal.userstest;

import com.sumber.barokah.jurnal.entity.master.s_users.Permissions;
import com.sumber.barokah.jurnal.entity.master.s_users.Roles;
import com.sumber.barokah.jurnal.entity.master.s_users.Users;
import com.sumber.barokah.jurnal.repository.master.users.PermissionsRepository;
import com.sumber.barokah.jurnal.repository.master.users.RolesRepository;
import com.sumber.barokah.jurnal.repository.master.users.UsersRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@SpringBootTest
public class UsersTest {

    @Autowired
    UsersRepository usersRepository;

    @Autowired
    RolesRepository rolesRepository;

    @Autowired
    PermissionsRepository permissionsRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Test
    void testInsertUsers() {

        Roles role = rolesRepository.findFirstByRoleId("r001")
                .orElseThrow(() -> new RuntimeException("role not found"));

        Users users = new Users();
        users.setUserId(UUID.randomUUID().toString());
        users.setUsername("candra");
        users.setUser_password(passwordEncoder.encode("rahasia"));
        users.setActive(true);
        users.setRole(role);

        usersRepository.save(users);

    }

    @Test
    void testUpdateUsers() {

        Roles role = rolesRepository.findFirstByRoleId("r004")
                .orElseThrow(() -> new RuntimeException("role not found"));

        Users users = usersRepository.findFirstByUsername("mamat")
                .orElseThrow(() -> new RuntimeException("user not found"));

//        users.setUsername("shinta");
        users.setRole(role);

        usersRepository.save(users);

    }

    @Test
    void testCreateRoles() {

        Roles roles = new Roles();
        roles.setRoleId("r003");
        roles.setRoleName("admin");

        rolesRepository.save(roles);

    }

    @Test
    void testCreatePermissions() {

        Permissions permissions = new Permissions();
        permissions.setPermissionsId("p004");
        permissions.setPermissionLabel("Hapus Data Transaksi");
        permissions.setPermissionValue("DELETE_TRANSAKSI");

        permissionsRepository.save(permissions);

    }

    @Test
    void createRolesSetAllPermissions() {

        Permissions p001 = permissionsRepository.findFirstByPermissionsId("p001")
                .orElseThrow(() -> new RuntimeException("permissions not found"));

        Permissions p002 = permissionsRepository.findFirstByPermissionsId("p002")
                .orElseThrow(() -> new RuntimeException("permissions not found"));

        Permissions p003 = permissionsRepository.findFirstByPermissionsId("p003")
                .orElseThrow(() -> new RuntimeException("permissions not found"));

        Permissions p004 = permissionsRepository.findFirstByPermissionsId("p004")
                .orElseThrow(() -> new RuntimeException("permissions not found"));

        List<Permissions> permissionsList = new ArrayList<>();
        permissionsList.add(p001);
        permissionsList.add(p002);
        permissionsList.add(p003);
        permissionsList.add(p004);

        Roles roles = new Roles();
        roles.setRoleId("r004");
        roles.setRoleName("CEO");
        roles.setPermissions(permissionsList);

        rolesRepository.save(roles);


    }


}
