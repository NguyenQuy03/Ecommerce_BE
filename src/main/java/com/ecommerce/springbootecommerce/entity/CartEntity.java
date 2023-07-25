    package com.ecommerce.springbootecommerce.entity;

    import lombok.AllArgsConstructor;
    import lombok.Getter;
    import lombok.NoArgsConstructor;
    import lombok.Setter;
    import org.springframework.data.mongodb.core.mapping.DBRef;
    import org.springframework.data.mongodb.core.mapping.Document;

    import java.util.List;

    @Document(collection = "Cart")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CartEntity extends BaseEntity{

    private String status;

    private AccountEntity account;

    @DBRef
    private List<OrderEntity> setOrders;
}

