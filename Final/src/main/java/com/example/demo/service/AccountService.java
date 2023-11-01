package com.example.demo.service;

import com.example.demo.entity.Account;

import java.util.List;

public interface AccountService {
    Account saveAccount(Account account);

    List<Account> fetchAllAccount();

    Account updateAccount(Account account, Integer accountId);

    void deleteAccount(Integer accountId);

    Account getAccountById(Integer accountId);

    boolean authorize(String username, String password);

    boolean emailExist(String username);
}
