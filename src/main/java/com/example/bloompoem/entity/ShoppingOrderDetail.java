package com.example.bloompoem.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class ShoppingOrderDetail {
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
