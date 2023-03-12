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
@CrossOrigin(value = "http://192.168.45.66:5500")
public class RestPickUpController {
    private final UserService userService;

    private final PickUpCartRepository pickUpCartRepository;

    private final PickUpService pickUpService;

    private final FloristRepository floristRepository;

    @GetMapping  (value = "/get_pick_up_cart")
    public ResponseEntity<?> getPickUpCart(@CookieValue(value = "Authorization") String token) {
        UserEntity user =  userService.tokenToUserEntity(token);
        List<PickUpCartResponse> pickUpCartResponseList = new ArrayList<>();
        List<PickUpCartEntity> pickUpCartEntities = pickUpCartRepository.findByUserEmail(user.getUserEmail());

        Integer floristNumber = 5;
        Integer flowerNumber = 5;
        FloristFlowerInterFace arrayList = floristRepository.searchFloristFlowerDetail(floristNumber,flowerNumber);

//        pickUpCartEntities.forEach(data -> {
//            pickUpCartResponseList
//                    .add(
//                            PickUpCartResponse
//                                    .builder()
//                                    .flowerNumber(data.getFlowerNumber())
//                                    .floristNumber(data.getFloristNumber())
//                                    .flowerCount(data.getFlowerCount())
//                                    .build());
//        });
        return ResponseEntity.ok().body(pickUpCartEntities);
    }

    @PostMapping  (value = "/pick_up_cart_update")
    public ResponseEntity<?> updatePickUpCart(@CookieValue(value = "Authorization") String token,@RequestBody List<PickUpCartRequest> pickUpCartRequestList) {
        if(pickUpCartRequestList.isEmpty()) throw new CustomException(ResponseCode.INVALID_REQUEST);




        pickUpService.pickUpCartDelete(userService.tokenToUserEntity(token).getUserEmail());
        pickUpCartRequestList.forEach(request -> {
            pickUpService.pickUpCartInsert(request,userService.tokenToUserEntity(token).getUserEmail());
        });

        return ResponseEntity.ok().body("성공");
    }

    @DeleteMapping (value = "/pick_up_cart_delete")
    public ResponseEntity<?> deletePickUpCart(@RequestParam String userEmail) {
        pickUpService.pickUpCartDelete(userEmail);

        return ResponseEntity.ok().body("성공");
    }
}
