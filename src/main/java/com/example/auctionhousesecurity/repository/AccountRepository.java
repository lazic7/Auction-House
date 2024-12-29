package com.example.auctionhousesecurity.repository;

import com.example.auctionhousesecurity.model.Account;

import java.util.List;

public interface AccountRepository {
    List<Account> findAll();
    Account findByEmail(String email);
}
