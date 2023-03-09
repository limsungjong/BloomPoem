package com.example.bloompoem.entity.ID;


import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@NoArgsConstructor
public class FloristProductID implements Serializable {
    private static final long serialVersionUID = 1L;
    @EqualsAndHashCode.Include
    private Integer floristNumber;

    @EqualsAndHashCode.Include
    private Integer flowerNumber;
}
