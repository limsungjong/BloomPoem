package com.example.bloompoem.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "shoppingOrder")
@SequenceGenerator(
        name = "SEQ_SHOPPING_ORDER_NUMBER",
        sequenceName = "SEQ_SHOPPING_ORDER_NUMBER",
        initialValue = 1, //시작값
        allocationSize = 1
)
public class ShoppingOrder {
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_SHOPPING_ORDER_NUMBER")
    @Id
    private int shoppingOrderNumber ;
    private String userEmail;
    private int shoppingOrderStatus;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate shoppingOrderDate;
    private int shoppingTotalPrice;
    private int shoppingRealPrice;


}
