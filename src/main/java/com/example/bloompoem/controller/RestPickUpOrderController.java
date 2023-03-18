package com.example.bloompoem.controller;

import com.example.bloompoem.repository.PickUpOrderDetailRepository;
import com.example.bloompoem.repository.PickUpOrderRepository;
import com.example.bloompoem.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/api/v1/pick_up_order")
@RequiredArgsConstructor
@CrossOrigin(value = "http://192.168.45.17:5500")
public class RestPickUpOrderController {

    private final PickUpOrderRepository pickUpOrderRepository;

    private final PickUpOrderDetailRepository pickUpOrderDetailRepository;

    private final UserService userService;

    @PostMapping(value = "/success/orderDetail")
    public ResponseEntity orderDetail(@RequestParam("orderNumber") Integer orderNumber) {

        System.out.println(orderNumber);

//        pickUpOrderDetailRepository.findById(orderNumber);

        return ResponseEntity.ok().body("성공");
    }
}
