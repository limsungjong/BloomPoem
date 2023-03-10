package com.example.bloompoem.dto;

import com.example.bloompoem.entity.FloristEntity;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;


@Getter
@Setter
@Builder
public class FloristDTO {
    private int floristNumber;
    private String floristName;
    private String floristAddress;
    private String floristPhoneNumber;
    private BigDecimal floristLatitude;
    private BigDecimal floristLongtitude;
    private String userEmail;

    public static FloristDTO toDTO(FloristEntity floristEntity) {
        return FloristDTO
                .builder()
                .floristNumber(floristEntity.getFloristNumber())
                .floristName(floristEntity.getFloristName())
                .floristAddress(floristEntity.getFloristAddress())
                .floristPhoneNumber(floristEntity.getFloristPhoneNumber())
                .floristLatitude(floristEntity.getFloristLatitude())
                .floristLongtitude(floristEntity.getFloristLongtitude())
                .build();
    }
}
