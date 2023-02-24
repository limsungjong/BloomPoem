package com.example.bloompoem.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;

@AllArgsConstructor
@Data
@NoArgsConstructor
@Entity
public class ProductEntity {
    @Id
    private Integer productNumber;
    private Integer productCategory;
    private String productName;
    private Integer productPrice;
    private Integer productQuantity;
    private String productMainImage;
    private String subImag1;
    private String subImage2;
}
