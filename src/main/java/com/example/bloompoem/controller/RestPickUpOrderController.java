package com.example.bloompoem.controller;

import com.example.bloompoem.domain.dto.ResponseCode;
import com.example.bloompoem.entity.Inter.OrderDetailResponse;
import com.example.bloompoem.exception.CustomException;
import com.example.bloompoem.repository.PickUpOrderDetailRepository;
import com.example.bloompoem.repository.PickUpOrderRepository;
import com.example.bloompoem.service.OrderService;
import com.example.bloompoem.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
    public ResponseEntity<List<?>> orderDetail(@RequestParam Integer orderNumber
            ,@CookieValue(value = "Authorization") String token
    ) {
        String userEmail = userService.tokenToUserEntity(token).getUserEmail();
        if(pickUpOrderRepository.findById(orderNumber).isPresent()) {
            if(orderService.countBouquet(orderNumber) == 0){
                return ResponseEntity.ok().body(orderService.getOderDetailResponseList(orderNumber, userEmail));
            }else{
                return ResponseEntity.ok(orderService.getOrderDetailBouquetList(orderNumber,userEmail));
            }
        }
        throw new CustomException(ResponseCode.NOT_FOUND_ORDER);
    }
}