package com.example.bloompoem.controller;

import com.example.bloompoem.domain.dto.PickUpOrderRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api/v1/pick_up_order")
public class RestPickUpOrderController {

    @PostMapping(value = "/startBuy")
    public ResponseEntity<?> startBuy(@RequestBody PickUpOrderRequest pickUpOrderRequest) {


        return ResponseEntity.ok().body("성공!");
    }
}
