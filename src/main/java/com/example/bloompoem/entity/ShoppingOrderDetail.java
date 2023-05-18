package com.example.bloompoem.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class ShoppingOrderDetail {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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
