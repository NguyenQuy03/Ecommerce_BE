package com.ecommerce.springbootecommerce.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Index;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "Product", indexes = {
    @Index(columnList = "category_id"),
    @Index(columnList = "name")
})
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProductEntity extends BaseEntity{
    
    @NotNull
    @Column(columnDefinition = "NVARCHAR(100)")
    private String name;
    
    @NotNull
    private String image;
    
    @Column(columnDefinition = "NVARCHAR(max)")
    private String description;
    
    @NotNull
    @Column(columnDefinition = "varchar(20)")
    private String status;
    
    @NotNull
    @Column(columnDefinition = "NVARCHAR(MAX)")
    private String specification;
    
    @OneToOne
    @JoinColumn(name = "account_id", referencedColumnName = "id")
    private AccountEntity account;

    @OneToOne
    @JoinColumn(name = "category_id", referencedColumnName = "id")
    private CategoryEntity category;

    @Transient
    private Long totalSold;
}
