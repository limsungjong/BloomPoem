package com.example.bloompoem.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.util.Date;

@DynamicUpdate
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@SequenceGenerator(
        name = "SEQ_SHOPPING_REVIEW_NUMBER",
        sequenceName = "SEQ_SHOPPING_REVIEW_NUMBER",
        initialValue = 1, //시작값
        allocationSize = 1
)
public class ShoppingReview {
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_SHOPPING_REVIEW_NUMBER")

    @Id
    private int shoppingReviewNumber ;
    @ManyToOne
    @JoinColumn(name="userEmail")
    private  UserEntity user;
    @ManyToOne
    @JoinColumn(name="productNumber")
    private ProductEntity product;
    @ManyToOne
    @JoinColumn(name="shoppingOrderNumber")
    private  ShoppingOrder shoppingOrder;
    private  String shoppingReviewContent;
    private  int  shoppingReviewScore;
    private Date shoppingReviewRegDate;
    private String shoppingReviewImage;

}
