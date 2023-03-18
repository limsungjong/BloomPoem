package com.example.bloompoem.domain.dto;


import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class PickUpOrderSuccessResponse {
    String userName;
    String flowerName;
    String flowerImage;
    String floristName;
    Integer flowerCount;
    String date;
    String time;
    String partner_order_id;
}
