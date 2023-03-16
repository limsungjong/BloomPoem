package com.example.bloompoem.domain.kakaoPay;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
@Builder
@ToString
public class PayAmount {

    private int total;
    private int tax_free;
    private int vat;
    private int point;
    private int discount;

}
