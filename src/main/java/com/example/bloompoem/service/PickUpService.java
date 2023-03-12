package com.example.bloompoem.service;

import com.example.bloompoem.domain.dto.PickUpCartRequest;
import com.example.bloompoem.domain.dto.ResponseCode;
import com.example.bloompoem.entity.PickUpCartEntity;
import com.example.bloompoem.exception.CustomException;
import com.example.bloompoem.repository.FloristProductRepository;
import com.example.bloompoem.repository.PickUpCartRepository;
import com.example.bloompoem.repository.PickUpOrderDetailRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PickUpService {
    private final FloristProductRepository floristProductRepository;

    private final PickUpCartRepository pickUpCartRepository;

    private final PickUpOrderDetailRepository pickUpOrderDetailRepository;

    private final UserService userService;

    @Transactional
    public void pickUpCartInsert(PickUpCartRequest request, String userEmail) {
        PickUpCartEntity optionalPickUpCart = pickUpCartRepository.findByUserEmailAndFlowerNumberAndFloristNumber(userEmail, request.getFlowerNumber(), request.getFloristNumber()).orElse(null);
        if(optionalPickUpCart != null) {
            optionalPickUpCart.setFloristNumber(request.getFloristNumber());
            optionalPickUpCart.setFlowerNumber(request.getFlowerNumber());
            optionalPickUpCart.setFlowerCount(request.getFlowerCount());
            optionalPickUpCart.setUserEmail(userEmail);
            pickUpCartRepository.save(optionalPickUpCart);
            return;
        }
        PickUpCartEntity pickUpCartEntity = new PickUpCartEntity();
        pickUpCartEntity.setFloristNumber(request.getFloristNumber());
        pickUpCartEntity.setFlowerNumber(request.getFlowerNumber());
        pickUpCartEntity.setFlowerCount(request.getFlowerCount());
        pickUpCartEntity.setUserEmail(userEmail);
        pickUpCartRepository.save(pickUpCartEntity);
    }


    @Transactional
    public void pickUpCartDelete(String userEmail) {
       List<PickUpCartEntity> pickUpCartEntities = pickUpCartRepository.findByUserEmail(userEmail);
       pickUpCartEntities.forEach(v -> {
           pickUpCartRepository.deleteById(v.getPickUpCartNumber());
       });
       pickUpCartRepository.flush();
    }

    public void pickUpCartReqCheck(PickUpCartRequest request) {
        if(ObjectUtils.isEmpty(request)) {

        }
    }
}
