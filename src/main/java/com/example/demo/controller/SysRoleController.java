package com.example.demo.controller;

import com.example.demo.model.Account;
import com.example.demo.model.SysRole;
import com.example.demo.repository.SysRoleRepository;
import com.example.demo.service.SysRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/role")
public class SysRoleController {
    @Autowired
    SysRoleService sysRoleService;

    @Autowired
    SysRoleRepository sysRoleRepository;

    @GetMapping
    public ResponseEntity<?> getAllSysRoles(){
        return ResponseEntity.ok(sysRoleRepository.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(
            @PathVariable("id") String id) {
        SysRole result = sysRoleRepository.findById(id).get();

        if (result == null) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
        }

        return ResponseEntity.ok(result);
    }

    @PostMapping
    public ResponseEntity<?> createRole(
            @RequestBody SysRole role) {
        SysRole createdRole = sysRoleRepository.save(role);
        return ResponseEntity.ok(createdRole);
    }

    @DeleteMapping("/{moduleId}")
    public ResponseEntity<?> deleteByModuleId (
            @PathVariable("moduleId") String moduleId) {
        sysRoleService.deleteAllByModuleId(moduleId);
        return ResponseEntity.ok().build();
    }
}
