package com.ecommerce.springbootecommerce.dto;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import lombok.Data;

@Data
public class AbstractDTO<E> {
    private Long id;
	private List<E> listResult = new ArrayList<>();

	private Date createdDate;
	private Date modifiedDate;
	private String createdBy;
	private String modifiedBy;
}
