package com.ecommerce.springbootecommerce.entity;

import lombok.Data;
import org.springframework.data.annotation.*;

import java.util.Date;

@Data
public class BaseEntity {

    @Id
    private String id;

    @CreatedBy
    private String createdBy;

    @CreatedDate
    private Date createdDate;

    @LastModifiedBy
    private String modifiedBy;

    @LastModifiedDate
    private Date modifiedDate;
}
