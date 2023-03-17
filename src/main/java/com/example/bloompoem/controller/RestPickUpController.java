package com.example.bloompoem.controller;

import com.example.bloompoem.domain.dto.PickUpCartRequest;
import com.example.bloompoem.domain.dto.PickUpCartResponse;
import com.example.bloompoem.domain.dto.ResponseCode;
import com.example.bloompoem.entity.Inter.FloristFlowerInterFace;
import com.example.bloompoem.entity.PickUpCartEntity;
import com.example.bloompoem.entity.UserEntity;
import com.example.bloompoem.exception.CustomException;
import com.example.bloompoem.repository.FloristRepository;
import com.example.bloompoem.repository.PickUpCartRepository;
import com.example.bloompoem.service.PickUpService;
import com.example.bloompoem.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RequestMapping(value = "/api/v1/pick_up")
@RequiredArgsConstructor
@RestController
@CrossOrigin(value = "http://192.168.1.135:5500")
public class RestPickUpController {
    private final UserService userService;

    private final PickUpCartRepository pickUpCartRepository;

    private final PickUpService pickUpService;

    private final FloristRepository floristRepository;

    @GetMapping(value = "/get_pick_up_cart")
    public ResponseEntity<?> getPickUpCart(@CookieValue(value = "Authorization") String token) {
        UserEntity user = userService.tokenToUserEntity(token);
        List<PickUpCartResponse> pickUpCartResponseList = new ArrayList<>();
        List<PickUpCartEntity> pickUpCartEntities = pickUpCartRepository.findByUserEmail(user.getUserEmail());

        pickUpCartEntities.forEach(data -> {
            FloristFlowerInterFace detail = floristRepository.searchFloristFlowerDetail(data.getFloristNumber(), data.getFlowerNumber());
            pickUpCartResponseList
                    .add(
                            PickUpCartResponse
                                    .builder()
                                    .userEmail(data.getUserEmail())
                                    .flowerName(detail.getFlowerName())
                                    .flowerNumber(data.getFlowerNumber())
                                    .floristNumber(data.getFloristNumber())
                                    .flowerCount(data.getFlowerCount())
                                    .floristMainImage(detail.getFloristMainImage())
                                    .floristProductPrice(detail.getFloristProductPrice())
                                    .pickUpCartNumber(data.getPickUpCartNumber())
                                    .build());
        });
        return ResponseEntity.ok().body(pickUpCartResponseList);
    }

    @PostMapping(value = "/pick_up_cart_update")
    public ResponseEntity<?> updatePickUpCart(@CookieValue(value = "Authorization") String token, @RequestBody List<PickUpCartRequest> pickUpCartRequestList) {
        if (pickUpCartRequestList.isEmpty()) throw new CustomException(ResponseCode.INVALID_REQUEST);

        pickUpService.pickUpCartDelete(userService.tokenToUserEntity(token).getUserEmail());
        pickUpCartRequestList.forEach(request -> {
            pickUpService.pickUpCartInsert(request, userService.tokenToUserEntity(token).getUserEmail());
        });

        return ResponseEntity.ok().body("성공");
    }

    @DeleteMapping(value = "/pick_up_cart_delete")
    public ResponseEntity<?> deletePickUpCart(@CookieValue(value = "Authorization") String token) {
        pickUpService.pickUpCartDelete(userService.tokenToUserEntity(token).getUserEmail());

        return ResponseEntity.ok().body("성공");
    }

    @PostMapping(value = "/pick_up_cart_delete_target")
    public ResponseEntity<?> deleteTargetCart(@CookieValue(value = "Authorization") String token, @RequestBody List<PickUpCartRequest> requestList) {
        for (PickUpCartRequest pickUpCartRequest : requestList) {
            pickUpService.pickUpCartTargetDelete(pickUpCartRequest, userService.tokenToUserEntity(token).getUserEmail());
        }
        return ResponseEntity.ok().body("성공");
    }

    @PostMapping(value = "/pick_up_cart_update_target")
    public ResponseEntity<?> updateTargetCart(@CookieValue(value = "Authorization") String token, @RequestBody PickUpCartRequest request) {
        pickUpService.pickUpCartInsert(request,userService.tokenToUserEntity(token).getUserEmail());
        return ResponseEntity.ok().body("성공!");
    }
}
