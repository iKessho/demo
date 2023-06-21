package com.example.demo.service;

import com.example.demo.model.Account;
import com.example.demo.model.SysRole;
import com.example.demo.repository.AccountRepository;
import com.example.demo.repository.SysRoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class AccountService {
    @Autowired
    AccountRepository accountRepository;

    @Autowired
    SysRoleRepository sysRoleRepository;

    public Account findByUserId(String id) {
        return accountRepository.findByUserIdIgnoreCase(id).orElse(null);
    }

    @Transactional
    public Account upsertAccount(Account account) {
        Account existingAccount = findByUserId(account.getUserId());
        if (existingAccount != null) {
            // do your whatever mapping, but i lazy so;
            existingAccount.setName(account.getName());
            existingAccount.setMobileNum(account.getMobileNum());
        } else {
            // again this should be done in mapper but i lazy so;
            existingAccount = new Account();
            existingAccount.setUserId(account.getUserId());
            existingAccount.setName(account.getName());
            existingAccount.setMobileNum(account.getMobileNum());
            existingAccount.setSysRoles(account.getSysRoles());
        }
        Set<SysRole> updatedRoles = new HashSet<>();

        for (SysRole sysRole : account.getSysRoles()) {
            Optional<SysRole> existingRole = sysRoleRepository.findByModuleIdIgnoreCaseAndSysRoleIdIgnoreCase(sysRole.getModuleId(),sysRole.getSysRoleId());
            if (existingRole.isPresent()) {
//                existingRole.get().addAccount(existingAccount);
                updatedRoles.add(existingRole.get());
            } else {
                SysRole newRole = sysRoleRepository.save(sysRole);
//                newRole.addAccount(account);
                updatedRoles.add(newRole);
            }
        }
        existingAccount.setSysRoles(updatedRoles);
        return accountRepository.save(existingAccount);
    }

    public void deleteAccount(String userId) {
        Account existingAccount = findByUserId(userId);
        accountRepository.delete(existingAccount);
    }

    public void deleteAllByModuleId(String moduleId) {
        List<SysRole> rolesToRemove =  sysRoleRepository.findByModuleIdIgnoreCase(moduleId);
        for (Account account : accountRepository.findDistinctBySysRolesModuleIdIgnoreCase(moduleId)) {
            Set<SysRole> newRoles = account.getSysRoles()
                    .stream()
                    .filter(sysRole -> !rolesToRemove.contains(sysRole))
                    .collect(Collectors.toSet());
            account.setSysRoles(newRoles);
            accountRepository.save(account);
        }
    }
}
