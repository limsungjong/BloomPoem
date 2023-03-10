package com.example.bloompoem.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@AllArgsConstructor
@Data
@NoArgsConstructor
public class KakaoReady {

    private String tid;
    private String next_redirect_pc_url;
    private Date created_at;
}
