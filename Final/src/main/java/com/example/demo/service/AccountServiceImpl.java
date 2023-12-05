package com.example.demo.service;

import com.example.demo.dto.AccountDetails;
import com.example.demo.entity.Account;
import com.example.demo.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class AccountServiceImpl implements AccountService {
    private final AccountRepository accountRepository;
    @Autowired
    public AccountServiceImpl(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }
    @Override
    public Account saveAccount(Account account) {
        return accountRepository.save(account);
    }

    @Override
    public List<Account> fetchAllAccount() {
        return (List<Account>) accountRepository.findAll();
    }

    @Override
    public Account updateAccount(Account account, Integer accountId) {
        return null;
    }

    @Override
    public void deleteAccount(Integer accountId) {
        accountRepository.deleteById(accountId);
    }

    @Override
    public Account getAccountById(Integer accountId) {
        return accountRepository.findById(accountId).orElse(null);
    }

    @Override
    public boolean authorize(String username, String password) {
        Account account = accountRepository.findByUsernameAndPassword(username, password);
        return account != null;
    }

    @Override
    public Account getAccountByUsername(String username){
        return this.accountRepository.findByUsername(username);
    }

    @Override
    public boolean emailExist(String username) {
        return accountRepository.existsByUsername(username);
    }


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // Kiểm tra xem account có tồn tại trong database không?
        Account account = accountRepository.findByUsername(username);
        if (account == null) {
            throw new UsernameNotFoundException("Account not found with username: " + username);
        }
        return new AccountDetails(account);
    }
}
