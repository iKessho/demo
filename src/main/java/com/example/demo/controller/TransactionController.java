package com.example.demo.controller;

import com.example.demo.model.Transaction;
import com.example.demo.service.TransactionService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/transaction")
public class TransactionController {

    @Autowired
    private TransactionService transactionService;

    @Autowired
    private ObjectMapper mapper;

    @PostMapping("null")
    public ResponseEntity<?> noTransaction(@RequestBody Transaction transaction) throws JsonProcessingException {
        Transaction resTransaction = transactionService.noTransactionalMethod(transaction);
        return ResponseEntity.ok("success" + mapper.writeValueAsString(resTransaction));
    }

    @PostMapping("public")
    public ResponseEntity<?> publicTransaction(@RequestBody Transaction transaction) throws JsonProcessingException {
        Transaction resTransaction = transactionService.transactionalOnPublicMethod(transaction);
        return ResponseEntity.ok("success" + mapper.writeValueAsString(resTransaction));
    }

    @PostMapping("private")
    public ResponseEntity<?> privateTransaction(@RequestBody Transaction transaction) throws JsonProcessingException {
        Transaction resTransaction = transactionService.transactionalOnPrivateMethodWrapper(transaction);
        return ResponseEntity.ok("success" + mapper.writeValueAsString(resTransaction));
    }

    @PostMapping("/nested")
    public ResponseEntity<?> nestedTransaction(@RequestBody Transaction transaction) throws JsonProcessingException {
        Transaction resTransaction = transactionService.transactionalNested(transaction);
        return ResponseEntity.ok("success" + mapper.writeValueAsString(resTransaction));
    }

    @GetMapping
    public ResponseEntity<?> ping() {
        return ResponseEntity.ok(new Transaction());
    }
}
