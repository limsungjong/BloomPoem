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
@SequenceGenerator(
        name = "SEQ_PICK_UP_CART_NUMBER",
        sequenceName = "SEQ_PICK_UP_CART_NUMBER",
        initialValue = 1,
        allocationSize = 1
)
public class PickUpCartEntity {
    @Id
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "SEQ_PICK_UP_CART_NUMBER"
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
}
