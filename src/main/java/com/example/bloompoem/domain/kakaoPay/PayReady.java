package com.example.bloompoem.domain.kakaoPay;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class PayReady {
    private String tid;
    private String next_redirect_pc_url;
    private String partner_order_id;
}
