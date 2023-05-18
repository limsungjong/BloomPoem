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
public class FlowerEntity {
    @Id
    @GeneratedValue(
            strategy = GenerationType.IDENTITY
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
