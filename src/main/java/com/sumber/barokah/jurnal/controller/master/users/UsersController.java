package com.sumber.barokah.jurnal.controller.master.users;

import com.sumber.barokah.jurnal.dto.WebResponse;
import com.sumber.barokah.jurnal.dto.master.users.CreateUsersRequest;
import com.sumber.barokah.jurnal.dto.master.users.UsersResponse;
import com.sumber.barokah.jurnal.service.UsersService;
import com.sumber.barokah.jurnal.utilities.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UsersController {

    @Autowired
    UsersService usersService;

    @PostMapping(
            path = "/api/user/register",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public WebResponse<UsersResponse> register(@RequestBody CreateUsersRequest request){

        UsersResponse usersResponse = usersService.register(request);

        return WebResponse.<UsersResponse>builder()
                .data(usersResponse)
                .status(HttpStatus.OK)
                .status_code(Constants.OK)
                .message(Constants.CREATE_MESSAGE)
                .build();

    }


}
