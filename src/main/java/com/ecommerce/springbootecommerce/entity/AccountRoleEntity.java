package com.ecommerce.springbootecommerce.entity;

import java.io.Serializable;

import javax.persistence.Embeddable;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Entity
@Data
@NoArgsConstructor
@Table(name = "Accounts_roles")
public class AccountRoleEntity {

    @EmbeddedId
    private AccountRoleEntityId id = new AccountRoleEntityId();

    @ManyToOne
    @MapsId("accountId")
    private AccountEntity account;

    @ManyToOne
    @MapsId("roleCode")
    private RoleEntity role;

    public AccountRoleEntity(AccountRoleEntityId id) {
      this.id = id;
    }

    @Embeddable
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class AccountRoleEntityId implements Serializable {
      private static final long serialVersionUID = 1L;

      private Long accountId;
      private String roleCode;
    }
}