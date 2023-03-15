package com.example.bloompoem.domain.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class PickUpOrderRequest {

    String userEmail;

    Integer flowerNumber;

    Integer flowerCount;

    Integer orderPrice;

    Integer floristNumber;

    Integer pickUpCartNumber;
}
