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
public class PickUpOrderDetail {
    @Id
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "SEQ_PICK_UP_ORDER_DETAIL_NUMBER"
    )
    private long pickUpOrderDetailNumber;

    @Column
    private long flowerNumber;
    @Column
    private String userEmail;
    @Column
    private long pickUpOrderNumber;
    @Column
    private long pickUpOrderDetailCount;
}
