package com.ecommerce.springbootecommerce.api.common.authenticate.payload.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GetPageRequest {
    private int page;
    private int size;
    private String type;
}
