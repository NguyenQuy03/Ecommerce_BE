package com.ecommerce.springbootecommerce.dto;

import java.util.Date;
import java.util.List;

import lombok.Data;

@Data
public class BaseDTO<T> {
    private Long id;
	private List<T> listResult;
	private Integer page;
	private Integer size;
	private Integer totalPage;
	private long totalItem;

	private Date createdDate;
	private Date modifiedDate;
	private String createdBy;
	private String modifiedBy;
}
