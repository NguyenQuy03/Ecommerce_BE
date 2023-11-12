package com.ecommerce.springbootecommerce.dto;

import java.util.Date;
import java.util.List;

import com.ecommerce.springbootecommerce.constant.enums.voucher.VoucherScope;
import com.ecommerce.springbootecommerce.constant.enums.voucher.VoucherStatus;
import com.ecommerce.springbootecommerce.constant.enums.voucher.VoucherType;
import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class VoucherDTO extends BaseDTO<VoucherDTO>{

    private String name;

    private String code;

    private VoucherType type;

    private Double value;

    @SuppressWarnings("unused")
    private String stringValue;

    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss", timezone = "Asia/Ho_Chi_Minh")
    private Date startDate;
    
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss", timezone = "Asia/Ho_Chi_Minh")
    private Date endDate;

    private Integer usageQuantity;

    private Integer quantityUsed;

    private Integer maxUsagePerUser;

    private Double minOrderTotalPrice;

    private VoucherStatus status;

    private VoucherScope voucherScope;

    private AccountDTO account;

    private List<ProductDTO> products;

    private List<CategoryDTO> categories;

    public String getStringValue() {
        if(this.type == VoucherType.PERCENTAGE) {
            return this.value + "%";
        }
        return "$" + this.value;
    }

    public void setStatusByDate(Date date) {
        if(date.compareTo(this.endDate) == 1) {
            this.status = VoucherStatus.EXPIRED;
        } else if(date.compareTo(this.startDate) == -1) {
           this.status = VoucherStatus.UPCOMING;
        } else {
            this.status = VoucherStatus.ONGOING;
        }
    }
}
