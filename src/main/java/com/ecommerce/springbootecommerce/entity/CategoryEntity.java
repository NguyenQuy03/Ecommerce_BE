package com.ecommerce.springbootecommerce.entity;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Lob;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "category")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CategoryEntity extends BaseEntity{
    
    @Column(name = "name")
    private String name;

    @Column(name = "code")
    private String code;

    @Column(name = "thumbnail")
    @Lob
    private byte[] thumbnail;

    @OneToMany(mappedBy = "category", fetch = FetchType.LAZY,
            cascade = CascadeType.ALL)
    private List<ProductEntity> products;

}
