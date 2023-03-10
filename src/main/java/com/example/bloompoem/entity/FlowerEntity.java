package com.example.bloompoem.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "FLOWER")
@NoArgsConstructor
@Builder
@Getter
@Setter
@AllArgsConstructor
@SequenceGenerator(
        name = "SEQ_FLOWER_NUMBER",
        sequenceName = "SEQ_FLOWER_NUMBER",
        initialValue = 72,
        allocationSize = 1
)
public class FlowerEntity {
    @Id
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "SEQ_FLOWER_NUMBER"
    )
    private int flowerNumber;

    @Column
    private String flowerName;

    @Column
    private String flowerLanguage;

    @Column
    private String flowerTag;

    @Column
    private String flowerSeason;

    @Column
    private String flowerColor;

    @Column
    private String flowerImage;
}
