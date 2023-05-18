package com.example.bloompoem.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;

@DynamicUpdate
@AllArgsConstructor
@Data
@NoArgsConstructor
@Entity(name="shoppingCart")
public class ShoppingCartEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int shoppingCartNumber;
    private String userEmail;
    private int shoppingCartCount;

    @ManyToOne
    @JoinColumn(name="productNumber")
    private ProductEntity product;

}
