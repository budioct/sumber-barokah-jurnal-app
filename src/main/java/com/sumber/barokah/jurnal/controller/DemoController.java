package com.sumber.barokah.jurnal.controller;

import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping(path = "/api")
public class DemoController {

    @PreAuthorize("hasAnyAuthority('SCOPE_VIEW_TRANSAKSI')")
    @GetMapping(
            path = "/sb/demo",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public Map<String, Object> demo(Authentication currentUser) {

        // Authentication // object untuk melihat Auhtentication

        Map<String, Object> data = new HashMap<>();

        data.put("key", "Demo");
//        data.put("current username", currentUser.getPrincipal());
        data.put("current Credential", currentUser.getCredentials());
//        data.put("current Authorities", currentUser.getAuthorities());
//        data.put("Authentication class", currentUser.getClass().getSimpleName());
//        data.put("Current Authentication", currentUser); // global

        return data;

    }

    @PreAuthorize("hasAnyAuthority('SCOPE_EDIT_TRANSAKSI')")
    @GetMapping(
            path = "/sb/userinfo",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public Map<String, Object> userinfo(Authentication currentUser) {

        // Authentication // object untuk melihat Auhtentication

        Map<String, Object> data = new HashMap<>();

        data.put("key", "User Info");
//        data.put("current username", currentUser.getPrincipal());
//        data.put("current Credential", currentUser.getCredentials());
//        data.put("current Authorities", currentUser.getAuthorities());
        data.put("Authentication class", currentUser.getClass().getSimpleName());
        data.put("Current Authentication", currentUser); // global

        return data;

    }




}
