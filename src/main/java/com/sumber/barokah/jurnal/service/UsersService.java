package com.sumber.barokah.jurnal.service;

import com.sumber.barokah.jurnal.dto.master.users.CreateUsersRequest;
import com.sumber.barokah.jurnal.dto.master.users.UsersResponse;
import com.sumber.barokah.jurnal.repository.master.users.UsersRepository;

public interface UsersService {

    UsersResponse register(CreateUsersRequest request);

}
