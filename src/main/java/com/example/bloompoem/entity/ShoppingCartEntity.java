package com.example.bloompoem.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@DynamicUpdate
@AllArgsConstructor
@Data
@NoArgsConstructor
@Entity(name="shoppingCart")
@SequenceGenerator(
        name = "SEQ_SHOPPING_CART_NUMBER",
        sequenceName = "SEQ_SHOPPING_CART_NUMBER",
        initialValue = 1, //시작값
        allocationSize = 1
)
public class ShoppingCartEntity {
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_SHOPPING_CART_NUMBER")
    @Id
    private int shoppingCartNumber;
    private String userEmail;
    private int shoppingCartCount;

    @ManyToOne
    @JoinColumn(name="productNumber")
    private ProductEntity product;

}
