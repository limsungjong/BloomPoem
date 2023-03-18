package com.example.bloompoem.entity.Inter;

import org.springframework.beans.factory.annotation.Value;

import java.util.Date;

public interface PickUpOrderResponse {
    @Value("#{target.pick_up_order_reservation_time}")
    String getPickUpOrderReservationTime();

    @Value("#{target.pick_up_order_reservation_date}")
    String getPickUpOrderReservationDate();

    @Value("#{target.user_email}")
    String getUserEmail();

    @Value("#{target.pick_up_order_date}")
    Date getPickUpOrderDate();

    @Value("#{target.pick_up_order_real_price}")
    Integer getPickUpOrderRealPrice();

    @Value("#{target.flower_name}")
    String getFlowerName();

    @Value("#{target.pick_up_order_number}")
    Integer getPickUpOrderNumber();

    @Value("#{target.flower_count}")
    Integer getFlowerCount();

    @Value("#{target.florist_number}")
    Integer getFloristNumber();
}
