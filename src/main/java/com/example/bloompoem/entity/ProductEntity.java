package com.example.bloompoem.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Entity;
import javax.persistence.Id;


@DynamicUpdate
@AllArgsConstructor
@Data
@NoArgsConstructor
@Entity(name = "product")
public class ProductEntity {
    @Id
    private int productNumber;
    private int productCategory;
    private String productName;
    private int productPrice;
    private int productQuantity;
    private String productMainImage;
    private String productSubImage1;

}
