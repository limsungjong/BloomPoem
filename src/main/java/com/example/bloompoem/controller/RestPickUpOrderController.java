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
public class RestPickUpOrderController {

    private final PickUpOrderRepository pickUpOrderRepository;

    private final PickUpOrderDetailRepository pickUpOrderDetailRepository;

    private final UserService userService;

    @PostMapping(value = "/success/orderDetail")
    public ResponseEntity orderDetail(@RequestParam String orderNumber,
                                      @CookieValue String token) {

        String userEmail = userService.tokenToUserEntity(token).getUserEmail();

        pickUpOrderDetailRepository.findById(Integer.parseInt(orderNumber));

        return ResponseEntity.ok().body("ㅁㄴㅇ");
    }
}
