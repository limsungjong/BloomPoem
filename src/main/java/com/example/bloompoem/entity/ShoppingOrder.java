package com.example.bloompoem.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "shoppingOrder")
public class ShoppingOrder {
    @Id
    private int shoppingOrderNumber ;
    private String userEmail;
    private int shoppingOrderStatus;
    private Date shoppingOrderDate;
    private int shoppingTotalPrice;
    private int shoppingRealPrice;


}
