package com.example.bloompoem.controller;

import com.example.bloompoem.domain.dto.PickUpCartRequest;
import com.example.bloompoem.domain.dto.PickUpCartResponse;
import com.example.bloompoem.domain.dto.ResponseCode;
import com.example.bloompoem.entity.PickUpCartEntity;
import com.example.bloompoem.entity.UserEntity;
import com.example.bloompoem.exception.CustomException;
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
@CrossOrigin(value = "http://192.168.45.66:5500")
public class RestPickUpController {
    private final UserService userService;

    private final PickUpCartRepository pickUpCartRepository;

    private final PickUpService pickUpService;

    @GetMapping  (value = "/get_pick_up_cart")
    public ResponseEntity<?> getPickUpCart(@CookieValue(value = "Authorization") String token) {
        UserEntity user =  userService.tokenToUserEntity(token);
        List<PickUpCartResponse> pickUpCartResponseList = new ArrayList<>();
        List<PickUpCartEntity> pickUpCartEntities = pickUpCartRepository.findByUserEmail(user.getUserEmail());
        pickUpCartEntities.forEach(data -> {
            pickUpCartResponseList
                    .add(
                            PickUpCartResponse
                                    .builder()
                                    .flowerNumber(data.getFlowerNumber())
                                    .floristNumber(data.getFloristNumber())
                                    .flowerCount(data.getFlowerCount())
                                    .build());
        });
        return ResponseEntity.ok().body(pickUpCartEntities);
    }

    @PostMapping  (value = "/pick_up_cart_update")
    public ResponseEntity<?> getPickUpCart(@RequestBody List<PickUpCartRequest> pickUpCartRequestList) {
//        @CookieValue(value = "Authorization") String token
//        String userEmail = userService.tokenToUserEntity(token).getUserEmail();

        String userEmail = "sung8881@naver.com";
        if(pickUpCartRequestList.isEmpty()) throw new CustomException(ResponseCode.INVALID_REQUEST);

        pickUpCartRequestList.forEach(request -> {
            pickUpService.pickUpCartInsert(request,userEmail);
        });

        return ResponseEntity.ok().body("성공");
    }

    @DeleteMapping (value = "/pick_up_cart_delete")
    public ResponseEntity<?> deleteUserCart(@RequestParam String userEmail) {
        pickUpService.pickUpCartDelete(userEmail);

        return ResponseEntity.ok().body("성공");
    }
}
