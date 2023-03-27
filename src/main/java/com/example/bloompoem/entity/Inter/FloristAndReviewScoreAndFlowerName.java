package com.example.bloompoem.entity.Inter;

import org.springframework.beans.factory.annotation.Value;

import java.math.BigDecimal;

public interface FloristAndReviewScoreAndFlowerName {
    @Value("#{target.florist_number}")
    Integer getFloristNumber();

    @Value("#{target.florist_name}")
    String getFloristName();

    @Value("#{target.florist_address}")
    String getFloristAddress();

    @Value("#{target.florist_latitude}")
    BigDecimal getFloristLatitude();

    @Value("#{target.florist_longtitude}")
    BigDecimal getFloristLongtitude();

    @Value("#{target.florist_phone_number}")
    String getFloristPhoneNumber();

    @Value("#{target.user_email}")
    String getUserEmail();

    @Value("#{target.florist_review_score}")
    Double getFloristReviewScore();

    @Value("#{target.florist_review_count}")
    Integer getFloristReviewCount();

    @Value("#{target.flower_name}")
    String getFlowerName();

    @Value("#{target.flower_color}")
    String getFlowerColor();
}
