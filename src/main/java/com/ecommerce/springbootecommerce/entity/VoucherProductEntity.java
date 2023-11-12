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
@Table(name = "voucher_products")
public class VoucherProductEntity {
    @EmbeddedId
    private VoucherProductEntityId id = new VoucherProductEntityId();

    @ManyToOne
    @MapsId("voucherId")
    private VoucherEntity voucher;

    @ManyToOne
    @MapsId("productId")
    private ProductEntity product;

    public VoucherProductEntity(VoucherProductEntityId id) {
      this.id = id;
    }

    @Embeddable
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class VoucherProductEntityId implements Serializable {
      private static final long serialVersionUID = 1L;

      private Long voucherId;
      private Long productId;
    }
}
