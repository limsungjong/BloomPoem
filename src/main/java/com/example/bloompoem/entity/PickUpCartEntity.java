package com.example.bloompoem.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "PICK_UP_CART")
@NoArgsConstructor
@Builder
@Getter
@ToString
@AllArgsConstructor
@Setter
public class PickUpCartEntity {
    @Id
    @GeneratedValue(
            strategy = GenerationType.IDENTITY
    )
    private int pickUpCartNumber;
    @Column
    private String userEmail;
    @Column
    private int floristNumber;
    @Column
    private int flowerNumber;
    @Column
    private int flowerCount;
    @ManyToOne
    @JoinColumn(name="bouquetNumber")
    private BouquetEntity bouquet;
}
