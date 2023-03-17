package com.example.bloompoem.domain.dto;


import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class PickUpOrderSuccess {
    String userName;
    String flowerName;
    String flowerImage;
    String date;
    String time;
    String flowerCount;
    String partner_order_id;
}
