package com.example.demo.service;

import com.example.demo.model.Child;
import com.example.demo.model.Parent;
import com.example.demo.model.Transaction;
import com.example.demo.repository.ChildRepository;
import com.example.demo.repository.ParentRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;

@Controller
public class TransactionService {
    private static final Logger logger = LogManager.getLogger(TransactionService.class);
    @Autowired
    private ParentRepository parentRepository;
    @Autowired
    private ChildRepository childRepository;

    @Transactional
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
                logger.info("Saving Parend with id: {}", resParent.getId());
                resTransaction.setParent(resParent);
            }
            if (transaction.getChild() != null) {
                Child resChild = childRepository.save(transaction.getChild());
                resTransaction.setChild(resChild);
            }
            Thread.sleep(3000);
        } catch (Exception e) {
            logger.warn("Base Exception: {}", e);
            return new Transaction();
        }
        return resTransaction;
    }

}
