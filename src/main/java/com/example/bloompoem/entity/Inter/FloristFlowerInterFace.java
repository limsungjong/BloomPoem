package com.example.bloompoem.entity.Inter;

import org.springframework.beans.factory.annotation.Value;

public interface FloristFlowerInterFace {


 @Value("#{target.flower_number}")
     long getFlowerNumber();


 @Value("#{target.flower_name}")
     String getFlowerName();


 @Value("#{target.flower_language}")
     String getFlowerLanguage();


 @Value("#{target.flower_tag}")
     String getFlowerTag();


 @Value("#{target.flower_season}")
     String getFlowerSeason();


 @Value("#{target.flower_color}")
     String getFlowerColor();

 @Value("#{target.flower_image}")
     String getFlowerImage();
}
