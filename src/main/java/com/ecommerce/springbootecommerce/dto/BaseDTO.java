package com.ecommerce.springbootecommerce.dto;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import lombok.Data;

@Data
public class BaseDTO<T> {
    private Long id;
	private List<T> listResult = new ArrayList<>();
	private Integer page;
	private Integer size;
	private Integer totalPage;
	private long totalItem;

	private Date createdDate;
	private Date modifiedDate;
	private String createdBy;
	private String modifiedBy;
}
