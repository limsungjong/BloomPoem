package com.example.bloompoem.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
@AllArgsConstructor
@Data
@NoArgsConstructor
@Entity(name ="product")
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
