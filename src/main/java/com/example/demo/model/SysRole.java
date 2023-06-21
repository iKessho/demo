package com.example.demo.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Table(
        uniqueConstraints=
        @UniqueConstraint(columnNames={"moduleId", "sysRoleId"})
)
@Entity
@Getter @Setter @NoArgsConstructor
public class SysRole {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String moduleId;
    private String sysRoleId;
    @JsonIgnore
    @ManyToMany(mappedBy = "sysRoles")
    private Set<Account> accounts = new HashSet<>();

    public void addAccount(Account account) {
        this.accounts.add(account);
        account.getSysRoles().add(this);
    }
}
