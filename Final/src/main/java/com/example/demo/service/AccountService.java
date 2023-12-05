package com.example.demo.service;

import com.example.demo.entity.Account;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

public interface AccountService extends UserDetailsService {
    Account saveAccount(Account account);

    List<Account> fetchAllAccount();

    Account updateAccount(Account account);

    void deleteAccount(Integer accountId);

    Account getAccountById(Integer accountId);

    boolean authorize(String username, String password);
    Account getAccountByUsername(String username);
    boolean emailExist(String username);
}
