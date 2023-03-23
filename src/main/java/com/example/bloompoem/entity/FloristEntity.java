package com.example.bloompoem.entity;

import com.example.bloompoem.dto.FloristDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "FLORIST")
@NoArgsConstructor
@Builder
@Getter
@AllArgsConstructor
@SequenceGenerator(
        name = "SEQ_FLORIST_NUMBER",
        sequenceName = "SEQ_FLORIST_NUMBER",
        initialValue = 62,
        allocationSize = 1
)
public class FloristEntity {

    @Id
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "SEQ_FLORIST_NUMBER"
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
