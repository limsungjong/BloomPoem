package com.example.bloompoem.domain.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class OrderReviewRequest {
    private int pickUpOrderNumber;
    private int floristNumber;
    private String floristReviewContent;
    private Character floristReviewScore;
    private String floristReviewImage;
}
