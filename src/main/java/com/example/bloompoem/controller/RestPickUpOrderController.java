package com.example.bloompoem.controller;

import com.example.bloompoem.domain.dto.ResponseCode;
import com.example.bloompoem.exception.CustomException;
import com.example.bloompoem.repository.PickUpOrderRepository;
import com.example.bloompoem.service.OrderService;
import com.example.bloompoem.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Objects;

@RestController
@RequestMapping(value = "/api/v1/pick_up_order")
@RequiredArgsConstructor
@CrossOrigin(value = "http://192.168.45.17:5500")
public class RestPickUpOrderController {

    private final PickUpOrderRepository pickUpOrderRepository;

    private final UserService userService;

    private final OrderService orderService;

    @PostMapping(value = "/success/orderDetail")
    @ResponseBody
    public ResponseEntity<HashMap> orderDetail(@RequestParam Integer orderNumber
            , @CookieValue(value = "Authorization") String token
    ) {
        String userEmail = userService.tokenToUserEntity(token).getUserEmail();
        HashMap list = new HashMap<String, Objects>();
        if (pickUpOrderRepository.findById(orderNumber).isPresent()) {
            list.put("flower", orderService.getOderDetailResponseList(orderNumber, userEmail));
            list.put("bouquet", orderService.getOrderDetailBouquetList(orderNumber, userEmail));
            return ResponseEntity.ok(list);
        }
        throw new CustomException(ResponseCode.NOT_FOUND_ORDER);
    }
}
