package com.example.bloompoem.service;

import com.example.bloompoem.domain.dto.PickUpCartRequest;
import com.example.bloompoem.entity.PickUpCartEntity;
import com.example.bloompoem.entity.PickUpOrderDetailEntity;
import com.example.bloompoem.entity.PickUpOrderEntity;
import com.example.bloompoem.repository.FloristProductRepository;
import com.example.bloompoem.repository.PickUpCartRepository;
import com.example.bloompoem.repository.PickUpOrderDetailRepository;
import com.example.bloompoem.repository.PickUpOrderRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.crypto.interfaces.PBEKey;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PickUpService {
    private final FloristProductRepository floristProductRepository;

    private final PickUpCartRepository pickUpCartRepository;

    private final PickUpOrderDetailRepository pickUpOrderDetailRepository;

    private final PickUpOrderRepository pickUpOrderRepository;

    private final UserService userService;

    Logger logger = LoggerFactory.getLogger(PickUpService.class);

    @Transactional
    public void pickUpCartInsert(PickUpCartRequest request, String userEmail) {
        PickUpCartEntity optionalPickUpCart = pickUpCartRepository.findByUserEmailAndFlowerNumberAndFloristNumber(userEmail, request.getFlowerNumber(), request.getFloristNumber()).orElse(null);
        if (optionalPickUpCart != null) {
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

    public void pickUpCartDeleteByPickOrderSeq(Integer orderSeq) {
        PickUpOrderEntity pickUpOrderEntity = pickUpOrderRepository.findById(orderSeq).orElseThrow();
        List<PickUpOrderDetailEntity> pickUpOrderDetailEntityList = pickUpOrderDetailRepository.findByPickUpOrderNumber(pickUpOrderEntity.getPickUpOrderNumber()).orElseThrow();

        // 픽업 오더 디테일 엔티티를 이용하여 픽업 카트 삭제
        pickUpOrderDetailEntityList.forEach(pickUpOrderDetailEntity -> {
            PickUpCartEntity entity = pickUpCartRepository
                    .findByUserEmailAndFlowerNumberAndFlowerCountAndFloristNumber(
                            pickUpOrderDetailEntity.getUserEmail(),
                            pickUpOrderDetailEntity.getFlowerNumber(),
                            pickUpOrderDetailEntity.getPickUpOrderDetailCount(),
                            pickUpOrderDetailEntity.getFloristNumber())
                    .orElseThrow();
            pickUpCartRepository.delete(entity);
        });
    }

    public void pickUpCartUpdate(PickUpCartRequest pick, String userEmail) {
        List<PickUpCartEntity> entityList = pickUpCartRepository.findByUserEmail(userEmail);

        entityList.forEach(cart -> {
            if(pick.getFlowerNumber().equals(cart.getFlowerNumber()) &&
                    pick.getFloristNumber().equals(cart.getFloristNumber())) {
                cart.setFlowerCount(pick.getFlowerCount());
            }
        });
    }

    @Transactional
    public void pickUpCartTargetDelete(PickUpCartRequest pick, String userEmail) {
        System.out.println(pick.getFlowerNumber());
        System.out.println(pick.getFlowerCount());
        System.out.println(pick.getFloristNumber());
        pickUpCartRepository.deleteByUserEmailAndAndFlowerNumberAndFloristNumber(userEmail,pick.getFlowerNumber(), pick.getFloristNumber());
    }
}
