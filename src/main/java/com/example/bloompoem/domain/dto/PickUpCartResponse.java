package com.example.bloompoem.domain.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class PickUpCartResponse {
    String userEmail;

    String flowerName;

    String flowerColor;

    Integer flowerNumber;

    Integer flowerCount;

    Integer floristNumber;

    String floristMainImage;

    Integer floristProductPrice;

    Integer pickUpCartNumber;
}
