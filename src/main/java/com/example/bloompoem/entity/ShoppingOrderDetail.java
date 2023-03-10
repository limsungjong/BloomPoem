package com.example.bloompoem.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@SequenceGenerator(
        name = "SEQ_SHOPPING_ORDER_DETAIL_NUMBER",
        sequenceName = "SEQ_SHOPPING_ORDER_DETAIL_NUMBER",
        initialValue = 1, //시작값
        allocationSize = 1
)
public class ShoppingOrderDetail {
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_SHOPPING_ORDER_DETAIL_NUMBER")
    @Id
    private int shoppingOrderDetailNumber;
    private int shoppingOrderDetailCount;
    private String userEmail;
    @ManyToOne
    @JoinColumn(name="shoppingOrderNumber")
    private ShoppingOrder shoppingOrder;
    @ManyToOne
    @JoinColumn(name="productNumber")
    private ProductEntity product;
}
