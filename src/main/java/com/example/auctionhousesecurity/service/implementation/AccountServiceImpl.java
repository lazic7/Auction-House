package com.example.auctionhousesecurity.service.implementation;

import com.example.auctionhousesecurity.model.Account;
import com.example.auctionhousesecurity.repository.AccountRepository;
import com.example.auctionhousesecurity.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AccountServiceImpl implements AccountService {

    private final AccountRepository accountRepository;

    @Autowired
    public AccountServiceImpl(
            final AccountRepository accountRepository
    ) {
        this.accountRepository = accountRepository;
    }

    //this method is used by authentication manager, it returns UserDetails
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return accountRepository.findByEmail(email);
    }

    @Override
    public Account loadAccountByEmail(String email) {
        return accountRepository.findByEmail(email);
    }

    @Override
    public List<Account> loadAllAccounts() {
        return accountRepository.findAll();
    }



}
