package com.example.bloompoem.entity;

import lombok.*;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "PICK_UP_ORDER")
@NoArgsConstructor
@Builder
@Getter
@AllArgsConstructor
@SequenceGenerator(
        name = "SEQ_PICK_UP_ORDER_NUMBER",
        sequenceName = "SEQ_PICK_UP_ORDER_NUMBER",
        initialValue = 1,
        allocationSize = 1
)
public class PickUpOrderEntity {
    @Id
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "SEQ_PICK_UP_ORDER_NUMBER"
    )
    private int pickUpOrderNumber;
    @Column
    private String userEmail;
    @Column
    private String pickUpOrderReservationTime;
    @Column
    private String pickUpOrderReservationDate;

    @Column
    @Temporal(TemporalType.TIMESTAMP)
    private Date pickUpOrderDate;

    @Column
    private int pickUpOrderTotalPrice;

    @Column
    private int pickUpOrderRealPrice;

    @Column
    @Setter
    private int pickUpOrderStatus;
}
