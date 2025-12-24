package com.shopoholics.payments.service;

import com.shopoholics.payments.model.Account;
import com.shopoholics.payments.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Optional;

@Service
public class PaymentService {
    
    @Autowired
    private AccountRepository accountRepository;
    
    public Optional<Account> getAccountByUserId(Long userId) {
        return accountRepository.findByUserId(userId);
    }
    
    public Account createAccount(Long userId) {
        // Проверяем, что у пользователя еще нет счета
        Optional<Account> existingAccount = accountRepository.findByUserId(userId);
        if (existingAccount.isPresent()) {
            throw new RuntimeException("Account already exists for user " + userId);
        }
        
        Account account = new Account(userId);
        return accountRepository.save(account);
    }
    
    @Transactional
    public Account deposit(Long userId, BigDecimal amount) {
        if (amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Deposit amount must be positive");
        }
        
        Account account = accountRepository.findByUserId(userId)
                .orElseThrow(() -> new RuntimeException("Account not found for user " + userId));
        
        account.setBalance(account.getBalance().add(amount));
        return accountRepository.save(account);
    }
    
    @Transactional
    public Account getBalance(Long userId) {
        return accountRepository.findByUserId(userId)
                .orElseThrow(() -> new RuntimeException("Account not found for user " + userId));
    }
    
    @Transactional
    public Account updateAccount(Account account) {
        return accountRepository.save(account);
    }
}