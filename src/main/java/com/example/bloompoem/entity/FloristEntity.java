package com.example.bloompoem.entity;

import com.example.bloompoem.dto.FloristDTO;
import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "FLORIST")
@NoArgsConstructor
@Builder
@Getter
@AllArgsConstructor
public class FloristEntity {
    @Setter
    @Id
    @GeneratedValue(
            strategy = GenerationType.IDENTITY
    )
    private int floristNumber;
    @Column
    private String floristName;
    @Column
    private String floristAddress;
    @Column
    private String floristPhoneNumber;

    @Column(precision = 15, scale = 10)
    private BigDecimal floristLatitude;

    @Column(precision = 15, scale = 10)
    private BigDecimal floristLongtitude;

    @Column
    private String userEmail;

    public static FloristEntity toEntity(FloristDTO floristDTO) {
        return FloristEntity
                .builder()
                .floristNumber(floristDTO.getFloristNumber())
                .floristName(floristDTO.getFloristName())
                .floristAddress(floristDTO.getFloristAddress())
                .floristPhoneNumber(floristDTO.getFloristPhoneNumber())
                .floristLatitude(floristDTO.getFloristLatitude())
                .floristLongtitude(floristDTO.getFloristLongtitude())
                .build();
    }
}
