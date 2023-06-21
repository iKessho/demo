package com.example.demo.service;

import com.example.demo.model.Child;
import com.example.demo.model.Parent;
import com.example.demo.model.Transaction;
import com.example.demo.repository.ChildRepository;
import com.example.demo.repository.ParentRepository;
import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;

import java.sql.SQLException;

@Slf4j
@Controller
public class TransactionService {
    @Autowired
    private ParentRepository parentRepository;
    @Autowired
    private ChildRepository childRepository;

    @Transactional(rollbackFor = {NullPointerException.class, SQLException.class, Exception.class})
    public Transaction transactionalOnPublicMethod(Transaction transaction) {
        return actualLogic(transaction);
    }

    public Transaction transactionalOnPrivateMethodWrapper(Transaction transaction) {
        return transactionalOnPrivateMethod(transaction);
    }

    public Transaction noTransactionalMethod(Transaction transaction) {
        return actualLogic(transaction);
    }

    @Transactional
    private Transaction transactionalOnPrivateMethod(Transaction transaction) {
        return actualLogic(transaction);
    }

    public Transaction transactionalNested(Transaction transaction) {
        return transactionalOnPublicMethod(transaction);
    }

    private Transaction actualLogic(Transaction transaction) {
        Transaction resTransaction = new Transaction();
        try {
            if (transaction.getParent() != null) {
                Parent resParent = parentRepository.save(transaction.getParent());
                log.info("Saving Parend with id: {}", resParent.getId());
                resTransaction.setParent(resParent);
            }
            if (transaction.getChild() != null) {
                Child resChild = childRepository.save(transaction.getChild());
                log.info("Saving Child with id: {}", resChild.getId());
                resTransaction.setChild(resChild);
            }
            Thread.sleep(3000);
        } catch (Exception e) {
            log.warn("Base Exception: " + e);
            return new Transaction();
        }
        return resTransaction;
    }

}
