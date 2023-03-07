package com.example.bloompoem.entity;

import com.example.bloompoem.dto.FloristDTO;
import lombok.*;

import javax.persistence.*;

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
    private long floristNumber;
    @Column
    private String floristName;
    @Column
    private String floristAddress;
    @Column
    private String floristPhoneNumber;
    @Column
    private long floristLatitude;
    @Column
    private long floristLongtitude;
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
                .userEmail(floristDTO.getUserEmail())
                .build();
    }
}
