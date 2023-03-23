package com.example.bloompoem.domain.dto;

import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;

@Getter
@Builder
public class FloristAndReviewAndFlowerNameResponse {
    private int floristNumber;
    private String floristName;
    private String floristAddress;
    private String floristPhoneNumber;
    private BigDecimal floristLatitude;
    private BigDecimal floristLongtitude;
    private String userEmail;
    private Double floristReviewScore;
    private Integer floristReviewCount;
    private String flowerName;
    private String flowerColor;
}
