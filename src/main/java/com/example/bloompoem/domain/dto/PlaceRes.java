package com.example.bloompoem.domain.dto;

import lombok.*;

@Getter
@Builder
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PlaceRes {
    private String name;
    private String address;
    private double latitude;
    private double longitude;
}
