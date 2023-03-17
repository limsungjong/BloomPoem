package com.example.bloompoem.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "PICK_UP_ORDER_DETAIL")
@NoArgsConstructor
@Builder
@Getter
@AllArgsConstructor
@SequenceGenerator(
        name = "SEQ_PICK_UP_ORDER_DETAIL_NUMBER",
        sequenceName = "SEQ_PICK_UP_ORDER_DETAIL_NUMBER",
        initialValue = 1,
        allocationSize = 1
)
public class PickUpOrderDetailEntity {
    @Id
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "SEQ_PICK_UP_ORDER_DETAIL_NUMBER"
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
}
