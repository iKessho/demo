package com.example.demo.repository;

import com.example.demo.model.SysRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SysRoleRepository extends JpaRepository<SysRole, String> {
    Optional<SysRole> findByModuleIdIgnoreCaseAndSysRoleIdIgnoreCase(String moduleId, String sysRoleId);

    List<SysRole> findByModuleIdIgnoreCase(String moduleId);
}
