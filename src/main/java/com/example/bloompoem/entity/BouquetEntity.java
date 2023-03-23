package com.example.bloompoem.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity(name = "bouquet")
@SequenceGenerator(
        name = "SEQ_BOUQUET_NUMBER",
        sequenceName = "SEQ_BOUQUET_NUMBER",
        initialValue = 1,
        allocationSize = 1
)
public class BouquetEntity {
    @Id
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "SEQ_BOUQUET_NUMBER"
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
}
