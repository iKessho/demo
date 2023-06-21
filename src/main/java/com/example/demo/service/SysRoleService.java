package com.example.demo.service;

import com.example.demo.model.SysRole;
import com.example.demo.repository.SysRoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SysRoleService {
    @Autowired
    SysRoleRepository sysRoleRepository;

    public void deleteAllByModuleId(String moduleId) {
        for (SysRole sysRole : sysRoleRepository.findByModuleIdIgnoreCase(moduleId)) {
            sysRole.setAccounts(null);
            sysRoleRepository.save(sysRole);
        }
    }
}
