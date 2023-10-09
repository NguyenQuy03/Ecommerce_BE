package com.ecommerce.springbootecommerce.dto.product;

import java.util.List;
import java.util.Map;

import com.ecommerce.springbootecommerce.dto.AccountDTO;
import com.ecommerce.springbootecommerce.dto.BaseDTO;
import com.ecommerce.springbootecommerce.dto.CategoryDTO;

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

    private long totalSold;

    private long totalStock;

    private String avgPrice;

    private Map<String, List<String>> variations;

    private String status;

    private Map<String, String> specification;

    private List<ProductItemDTO> productItems;    

}
