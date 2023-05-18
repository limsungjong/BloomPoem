package com.example.bloompoem.entity;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "PICK_UP_ORDER")
@NoArgsConstructor
@Builder
@Getter
@AllArgsConstructor
public class PickUpOrderEntity {
    @Id
    @GeneratedValue(
            strategy = GenerationType.IDENTITY
    )
    private int pickUpOrderNumber;
    @Column
    private String userEmail;
    @Column
    private String pickUpOrderReservationTime;
    @Column
    private String pickUpOrderReservationDate;

    @Column
    private LocalDate pickUpOrderDate;

    @Column
    private int pickUpOrderTotalPrice;

    @Column
    private int pickUpOrderRealPrice;

    @Column
    @Setter
    private int pickUpOrderStatus;
}
