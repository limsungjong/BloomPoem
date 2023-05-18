package com.example.bloompoem.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDate;

@Data
@DynamicUpdate
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "shoppingOrder")
public class ShoppingOrder {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private int shoppingOrderNumber ;
    private String userEmail;
    private int shoppingOrderStatus;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate shoppingOrderDate;
    private int shoppingTotalPrice;
    private int shoppingRealPrice;


}
