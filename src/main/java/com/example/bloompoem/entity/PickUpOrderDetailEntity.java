package com.example.bloompoem.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "PICK_UP_ORDER_DETAIL")
@NoArgsConstructor
@Builder
@Getter
@AllArgsConstructor
public class PickUpOrderDetailEntity {
    @Id
    @GeneratedValue(
            strategy = GenerationType.IDENTITY
    )
    private int pickUpOrderDetailNumber;

    @Column
    private int flowerNumber;
    @Column
    private int floristNumber;
    @Column
    private String userEmail;
    @Column
    private int pickUpOrderNumber;
    @Column
    private int pickUpOrderDetailCount;
    @ManyToOne
    @JoinColumn(name="bouquetNumber")
    private BouquetEntity bouquet;
}
