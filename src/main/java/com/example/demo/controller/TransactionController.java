package com.example.demo.controller;

import com.example.demo.model.Transaction;
import com.example.demo.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/transaction")
public class TransactionController {

    @Autowired
    private TransactionService transactionService;

    @PostMapping("null")
    public ResponseEntity<?> noTransaction(@RequestBody Transaction transaction){
        Transaction resTransaction = transactionService.noTransactionalMethod(transaction);
        return ResponseEntity.ok("success");
    }

    @PostMapping("public")
    public ResponseEntity<?> publicTransaction(@RequestBody Transaction transaction){
        Transaction resTransaction = transactionService.transactionalOnPublicMethod(transaction);
        return ResponseEntity.ok("success");
    }

    @PostMapping("private")
    public ResponseEntity<?> privateTransaction(@RequestBody Transaction transaction){
        Transaction resTransaction = transactionService.transactionalOnPrivateMethodWrapper(transaction);
        return ResponseEntity.ok("success");
    }

    @PostMapping("/nested")
    public ResponseEntity<?> nestedTransaction(@RequestBody Transaction transaction){
        Transaction resTransaction = transactionService.transactionalNested(transaction);
        return ResponseEntity.ok("success");
    }

    @GetMapping
    public ResponseEntity<?> ping() {
        return ResponseEntity.ok(new Transaction());
    }
}
