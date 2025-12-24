package com.shopoholics.payments.controller;

import com.shopoholics.payments.model.Account;
import com.shopoholics.payments.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@RestController
@RequestMapping("/api/payments")
public class PaymentController {
    
    @Autowired
    private PaymentService paymentService;
    
    @PostMapping("/accounts")
    public ResponseEntity<Account> createAccount(@RequestParam Long userId) {
        try {
            Account account = paymentService.createAccount(userId);
            return ResponseEntity.ok(account);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        }
    }
    
    @PostMapping("/accounts/{userId}/deposit")
    public ResponseEntity<Account> deposit(@PathVariable Long userId, @RequestParam BigDecimal amount) {
        try {
            Account account = paymentService.deposit(userId, amount);
            return ResponseEntity.ok(account);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }
    
    @GetMapping("/accounts/{userId}/balance")
    public ResponseEntity<BigDecimal> getBalance(@PathVariable Long userId) {
        try {
            Account account = paymentService.getBalance(userId);
            return ResponseEntity.ok(account.getBalance());
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
}