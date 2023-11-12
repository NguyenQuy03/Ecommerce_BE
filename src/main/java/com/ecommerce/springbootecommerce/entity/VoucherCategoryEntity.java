package com.ecommerce.springbootecommerce.entity;

import java.io.Serializable;

import javax.persistence.Embeddable;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@Table(name = "voucher_categories")
public class VoucherCategoryEntity {

    @EmbeddedId
    private VoucherCategoryEntityId id = new VoucherCategoryEntityId();

    @ManyToOne
    @MapsId("voucherId")
    private VoucherEntity voucher;

    @ManyToOne
    @MapsId("categoryId")
    private CategoryEntity category;

    public VoucherCategoryEntity(VoucherCategoryEntityId id) {
      this.id = id;
    }

    @Embeddable
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class VoucherCategoryEntityId implements Serializable {
      private static final long serialVersionUID = 1L;

      private Long voucherId;
      private Long categoryId;
    }
}
