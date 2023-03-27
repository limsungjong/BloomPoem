package com.example.bloompoem.domain.dto;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;

@Getter
@Builder
public class PickUpOrderDetailDTO {
    String pickUpOrderReservationTime;
    String pickUpOrderReservationDate;
    String userEmail;
    LocalDate pickUpOrderDate;
    Integer pickUpOrderRealPrice;
    String flowerName;
    Integer flowerNumber;
    Integer pickUpOrderNumber;
    Integer pickUpOrderDetailCount;
    Integer floristNumber;
    String floristMainImage;
    String floristProductPrice;
    String floristName;
}