package com.ecommerce.springbootecommerce.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import com.ecommerce.springbootecommerce.constant.enums.voucher.VoucherScope;
import com.ecommerce.springbootecommerce.constant.enums.voucher.VoucherStatus;
import com.ecommerce.springbootecommerce.constant.enums.voucher.VoucherType;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "Voucher")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class VoucherEntity extends BaseEntity{
    
    @NotNull
    @Column(columnDefinition = "varchar(100)")
    private String name;

    @NotNull
    @Column(columnDefinition = "varchar(20)")
    private String code;

    @NotNull
    @Column(columnDefinition = "varchar(20)")
    @Enumerated(EnumType.STRING)
    private VoucherType type;
    
    @NotNull
    @Column()
    private double value;
    
    @NotNull
    @Column(columnDefinition = "datetime2(0)")
    private Date startDate;
    
    @NotNull
    @Column(columnDefinition = "datetime2(0)")
    private Date endDate;
    
    @NotNull
    @Column(name = "usage_quantity")
    private int usageQuantity;
    
    @NotNull
    @Column(name = "quantity_used")
    private int quantityUsed = 0;
    
    @NotNull
    @Column(name = "max_usage_per_user")
    private int maxUsagePerUser = 5;

    @NotNull
    @Column(name = "min_order_total_price")
    private double minOrderTotalPrice;

    @NotNull
    @Column(columnDefinition = "varchar(20)", name = "status")
    @Enumerated(EnumType.STRING)
    private VoucherStatus status;

    @NotNull
    @Column(columnDefinition = "varchar(20)", name = "voucher_scope")
    @Enumerated(EnumType.STRING)
    private VoucherScope voucherScope = VoucherScope.ALL;

    @OneToOne
    @JoinColumn(name = "account_id", referencedColumnName = "id")
    private AccountEntity account;
}
