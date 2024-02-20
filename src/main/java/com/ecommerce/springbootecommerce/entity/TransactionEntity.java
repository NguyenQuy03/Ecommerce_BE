package com.ecommerce.springbootecommerce.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import com.ecommerce.springbootecommerce.constant.enums.transaction.TransactionStatus;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "Transaction")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TransactionEntity extends BaseEntity {

    @NotNull
    @Column(columnDefinition = "varchar(20)", name = "status")
    @Enumerated(EnumType.STRING)
    private TransactionStatus status;

    @NotNull
    private Double totalPayment = 0D;
}
