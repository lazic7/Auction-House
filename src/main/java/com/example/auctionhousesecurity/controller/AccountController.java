package com.example.auctionhousesecurity.controller;

import com.example.auctionhousesecurity.dto.mapper.EntityDTOMapper;
import com.example.auctionhousesecurity.dto.response.AccountResponseDTO;
import com.example.auctionhousesecurity.model.Account;
import com.example.auctionhousesecurity.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/accounts")
public class AccountController {
    private final AccountService accountService;

    private final EntityDTOMapper<Account, AccountResponseDTO> accountDTOMapper;

    @Autowired
    public AccountController(
            final AccountService accountService,
            final EntityDTOMapper<Account, AccountResponseDTO> accountDTOMapper
    ) {
        this.accountService = accountService;
        this.accountDTOMapper = accountDTOMapper;
    }

    @GetMapping("/all")
    public ResponseEntity<List<AccountResponseDTO>> getAccounts() {
        return ResponseEntity.status(HttpStatus.OK).body(
                accountDTOMapper.mapToList(accountService.loadAllAccounts())
        );
    }
}
