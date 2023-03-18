package com.example.bloompoem.controller;

import com.example.bloompoem.domain.dto.ResponseCode;
import com.example.bloompoem.entity.Inter.OrderDetailResponse;
import com.example.bloompoem.exception.CustomException;
import com.example.bloompoem.repository.PickUpOrderDetailRepository;
import com.example.bloompoem.repository.PickUpOrderRepository;
import com.example.bloompoem.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/api/v1/pick_up_order")
@RequiredArgsConstructor
@CrossOrigin(value = "http://192.168.45.17:5500")
public class RestPickUpOrderController {

    private final PickUpOrderRepository pickUpOrderRepository;

    private final PickUpOrderDetailRepository pickUpOrderDetailRepository;

    private final UserService userService;

    @PostMapping(value = "/success/orderDetail")
    @ResponseBody
    public ResponseEntity<List<OrderDetailResponse>> orderDetail(@RequestParam Integer orderNumber
//            ,@CookieValue(value = "Authorization") String token
    ) {
//        String userEmail = userService.tokenToUserEntity(token).getUserEmail();
        if(pickUpOrderRepository.findById(orderNumber).isPresent()) {
            System.out.println(orderNumber);
            List<OrderDetailResponse> arrayList = pickUpOrderRepository.searchPickUpOrderSuccessResponse("sung8881@naver.com", orderNumber);
            return ResponseEntity.ok().body(arrayList);
        }
        throw new CustomException(ResponseCode.NOT_FOUND_ORDER);
    }
}