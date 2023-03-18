package com.example.bloompoem.domain.dto;

import com.example.bloompoem.domain.kakaoPay.PayOrderProduct;
import lombok.Getter;

import java.util.List;

@Getter
public class PickUpDateAndTImeRequest {
    String date;
    String time;
    List<PayOrderProduct> orderList;
}
