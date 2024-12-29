package com.example.auctionhousesecurity.service;

import com.example.auctionhousesecurity.model.Account;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

public interface AccountService extends UserDetailsService {

    List<Account> loadAllAccounts();

    Account loadAccountByEmail(String email);

}
