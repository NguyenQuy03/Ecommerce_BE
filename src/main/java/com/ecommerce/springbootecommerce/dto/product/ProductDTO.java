package com.ecommerce.springbootecommerce.dto.product;

import java.util.List;
import java.util.Map;

import com.ecommerce.springbootecommerce.dto.BaseDTO;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductDTO extends BaseDTO<ProductDTO>{
    private String accountId;

    private String categoryId;

    private String name;

    private String image;

    private String description;

    private long totalSold;

    private long totalStock;

    private String avgPrice;

    private Map<String, List<String>> variations;

    private String status;

    private List<Map<String, String>> specification;

    private List<Map<String, String>> productItemsData;    

    private List<ProductItemDTO> productItems;    

}
