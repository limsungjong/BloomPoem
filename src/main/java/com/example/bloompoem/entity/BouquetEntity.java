package com.example.bloompoem.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity(name = "bouquet")
public class BouquetEntity {
    @Id
    @GeneratedValue(
            strategy = GenerationType.IDENTITY
    )
    private int bouquetNumber;
    @ManyToOne
    @JoinColumn(name="floristNumber")
    private FloristEntity florist;
    @ManyToOne
    @JoinColumn(name="userEmail")
    private UserEntity user;
    private int bouquetPrice;
    private String bouquetMainImage;
    @ManyToOne
    @JoinColumn(name="bouquetColorRgb")
    private BouquetColor bouquetColor;

}
