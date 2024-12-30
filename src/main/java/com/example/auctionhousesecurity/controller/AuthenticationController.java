package com.example.auctionhousesecurity.controller;

import com.example.auctionhousesecurity.dto.request.LoginRequestDTO;
import com.example.auctionhousesecurity.service.AccountService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/authentication")
public class AuthenticationController {

    private final AccountService accountService;

    private final AuthenticationManager authenticationManager;

    @Autowired
    public AuthenticationController(
            AccountService accountService,
            AuthenticationManager authenticationManager
    ) {
        this.accountService = accountService;
        this.authenticationManager = authenticationManager;
    }


    @PostMapping("/login")
    public ResponseEntity<String> login(@Valid @RequestBody final LoginRequestDTO dto) {

        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        dto.getEmail(),
                        dto.getPassword()
                )
        );

        /*
         * TODO implement JWT service that will generate JWT token
         *  findByEmail removed because not needed, authenticationManager.authenticate validates authentication
         *  authentication.authenticate does not save user to Security Context Holder -> JWT filter
         *  authenticationManager.authenticate throws AuthenticationException
         *  maybe JWT filter doesn't need to call authenticate or findUserByEmail again
         */

        return ResponseEntity.status(HttpStatus.OK).body("ej00esji - example");
    }
}
