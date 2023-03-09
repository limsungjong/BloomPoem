package com.example.bloompoem.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "shoppingOrder")
@SequenceGenerator(
        name = "SEQ_SHOPPING_ORDER_NUMBER",
        sequenceName = "SEQ_SHOPPING_ORDER_NUMBER",
        initialValue = 1, //시작값
        allocationSize = 1
)
public class ShoppingOrder {
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_SHOPPING_ORDER_NUMBER")
    @Id
    private int shoppingOrderNumber ;
    private String userEmail;
    private int shoppingOrderStatus;
    private Date shoppingOrderDate;
    private int shoppingTotalPrice;
    private int shoppingRealPrice;

}
