package com.example.bloompoem.entity;

import com.example.bloompoem.entity.ID.FloristProductID;
import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "FLORIST_PRODUCT")
@NoArgsConstructor
@Builder
@Getter
@AllArgsConstructor
@IdClass(FloristProductID.class)
public class FloristProductEntity {

    @Id
    @Column
    private int floristNumber;
    @Id
    @Column
    private int flowerNumber;
    @Column
    private int floristProductPrice;

    @Column
    private int floristProductQuantity;

    @Column
    private String floristMainImage;

    @Column
    private String floristSubImage1;

    @Column
    private String floristSubImage2;
}