package com.sumber.barokah.jurnal.service.impl.users;

import com.sumber.barokah.jurnal.dto.master.users.CreateUsersRequest;
import com.sumber.barokah.jurnal.dto.master.users.RolesResponse;
import com.sumber.barokah.jurnal.dto.master.users.UsersResponse;
import com.sumber.barokah.jurnal.entity.master.s_users.Roles;
import com.sumber.barokah.jurnal.entity.master.s_users.Users;
import com.sumber.barokah.jurnal.repository.master.users.RolesRepository;
import com.sumber.barokah.jurnal.repository.master.users.UsersRepository;
import com.sumber.barokah.jurnal.service.UsersService;
import com.sumber.barokah.jurnal.service.ValidationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.LinkedList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Slf4j
@Service
public class UsersServiceImpl implements UsersService {

    @Autowired
    ValidationService validationService;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    UsersRepository usersRepository;

    @Autowired
    RolesRepository rolesRepository;

    @Transactional
    public UsersResponse register(CreateUsersRequest request) {

        validationService.validate(request);

        Roles roles = rolesRepository.findFirstByRoleId("r001")
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "role not found"));

        List<Roles> rolesList = new LinkedList<>();
        rolesList.add(roles);

        Users users = new Users();
        users.setUsername(request.getUsername());
        users.setActive(true);

        Example<Users> example = Example.of(users);
        if (usersRepository.exists(example)) {

            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Username already created");
        }

        users.setUserId(UUID.randomUUID().toString());
        users.setPassword(passwordEncoder.encode(request.getPassword()));
        users.setRoles(rolesList);

        usersRepository.save(users); // Save DB

        return toUsersResponse(users);
    }

    private UsersResponse toUsersResponse(Users users) {
        return UsersResponse.builder()
                .userId(users.getUserId())
                .username(users.getUsername())
                .password("PROTECTED")
                .active(users.isActive())
                .roles(users.getRoles().stream().map(this::toRolesResponse).collect(Collectors.toList()))
                .build();
    }

    private RolesResponse toRolesResponse(Roles roles) {
        return RolesResponse.builder()
                .roleId(roles.getRoleId())
                .roleName(roles.getRoleName())
                .build();
    }

}
