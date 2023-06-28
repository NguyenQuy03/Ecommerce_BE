package com.ecommerce.springbootecommerce.entity;

import java.io.Serializable;

import javax.persistence.Embeddable;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.persistence.Table;


@Entity
@Table(name = "ACCOUNT_ROLE")
public class AccountRoleEntity {

    @EmbeddedId
    private AccountRoleEntityId id = new AccountRoleEntityId();

    @ManyToOne
    @MapsId("accountId")
    private AccountEntity account;

    @ManyToOne
    @MapsId("roleCode")
    private RoleEntity role;
    
    public AccountRoleEntity() {
    }
    
    public AccountRoleEntity(AccountRoleEntityId id) {
        this.id = id;
    }

    public AccountEntity getAccount() {
        return account;
    }

    public RoleEntity getRole() {
        return role;
    }
    
    public void setAccount(AccountEntity accountEntity) {
        this.account = accountEntity;
    }
    
    public void setRole(RoleEntity roleEntity) {
        this.role = roleEntity;
    }

    @Embeddable
    public static class AccountRoleEntityId implements Serializable {

      private static final long serialVersionUID = 1L;

      private Long accountId;
      private String roleCode;

      public AccountRoleEntityId() {}

      public AccountRoleEntityId(Long accountId, String roleCode) {
        super();
        this.accountId = accountId;
        this.roleCode = roleCode;
      }

      public String getRoleCode() {
        return roleCode;
      }

      public Long getAccountId() {
        return accountId;
      }

      public void setAccountId(Long accountId) {
        this.accountId = accountId;
      }

      public void setRoleCode(String roleCode) {
        this.roleCode = roleCode;
      }
    }
}
