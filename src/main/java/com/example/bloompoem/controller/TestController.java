package com.example.bloompoem.controller;

import com.example.bloompoem.repository.*;
import com.example.bloompoem.service.PickUpService;
import com.example.bloompoem.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "http://192.168.45.148:5500/")
@Controller
@PropertySource("classpath:app.properties")
public class TestController {

    @Autowired
    PickUpOrderRepository pickUpOrderRepository;

    @Autowired
    FlowerRepository flowerRepository;

    @Autowired
    PickUpService pickUpService;

    @Autowired
    FloristProductRepository floristProductRepository;

    @Autowired
    PickUpCartRepository pickUpCartRepository;

    @Autowired
    UserService userService;

    @Autowired
    PickUpOrderDetailRepository pickUpOrderDetailRepository;

    @Autowired
    FloristReviewRepository floristReviewRepository;

    @Autowired
    FloristRepository floristRepository;

    @Value("#{environment['jwt.secret']}")
    private String secretKey;
}
