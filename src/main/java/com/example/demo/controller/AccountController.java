package com.example.demo.controller;

import com.example.demo.model.Account;
import com.example.demo.repository.AccountRepository;
import com.example.demo.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/account")
public class AccountController {
    @Autowired
    AccountService accountService;

    @Autowired
    AccountRepository accountRepository;

    @GetMapping
    public ResponseEntity<?> getAllAccounts() {
        return ResponseEntity.ok(accountRepository.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> searchById(
            @PathVariable("id") String id) {
        Account result = accountService.findByUserId(id);

        if (result == null) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
        }

        return ResponseEntity.ok(result);
    }

    @PostMapping
    public ResponseEntity<?> createAccount(
            @RequestBody Account account) {
        Account createdAccount = accountService.upsertAccount(account);
        return ResponseEntity.ok(createdAccount);
    }

    @DeleteMapping ("/{userId}")
    public ResponseEntity<?> deleteAccount(
            @PathVariable("userId") String userId) {
        accountService.deleteAccount(userId);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping ("/module/{moduleId}")
    public ResponseEntity<?> removeRoles(
            @PathVariable("moduleId") String moduleId) {
        accountService.deleteAllByModuleId(moduleId);
        return ResponseEntity.ok().build();
    }
}
