package com.example.bloompoem.dto;

import com.example.bloompoem.entity.FloristEntity;
import lombok.Builder;
import lombok.Getter;


@Getter
@Builder
public class FloristDTO {
    private long floristNumber;
    private String floristName;
    private String floristAddress;
    private String floristPhoneNumber;
    private long floristLatitude;
    private long floristLongtitude;
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
                .userEmail(floristEntity.getUserEmail())
                .build();
    }
}
