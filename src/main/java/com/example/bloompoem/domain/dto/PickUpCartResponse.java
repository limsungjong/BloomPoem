package com.example.bloompoem.domain.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class PickUpCartResponse {
    String userEmail;

    Integer flowerNumber;

    Integer flowerCount;

    Integer floristNumber;
}
