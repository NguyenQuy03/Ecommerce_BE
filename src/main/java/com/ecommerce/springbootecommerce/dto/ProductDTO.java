package com.ecommerce.springbootecommerce.dto;

import java.util.List;
import java.util.Map;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductDTO extends BaseDTO<ProductDTO>{
    
    private AccountDTO account;
    private CategoryDTO category;

    private String name;

    private String image;

    private String description;

    private Long totalSold;

    private Long totalStock;

    private Double revenue;

    private String avgPrice;

    private Map<String, List<String>> variations;

    private String status;

    private Map<String, String> specification;

    private List<ProductItemDTO> productItems;    

}
