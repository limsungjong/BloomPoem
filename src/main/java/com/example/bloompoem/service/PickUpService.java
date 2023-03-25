package com.example.bloompoem.service;

import com.example.bloompoem.domain.dto.PickUpCartRequest;
import com.example.bloompoem.domain.dto.ResponseCode;
import com.example.bloompoem.entity.PickUpCartEntity;
import com.example.bloompoem.entity.PickUpOrderDetailEntity;
import com.example.bloompoem.exception.CustomException;
import com.example.bloompoem.repository.FloristProductRepository;
import com.example.bloompoem.repository.PickUpCartRepository;
import com.example.bloompoem.repository.PickUpOrderDetailRepository;
import com.example.bloompoem.repository.PickUpOrderRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PickUpService {
    private final FloristProductRepository floristProductRepository;

    private final PickUpCartRepository pickUpCartRepository;

    private final PickUpOrderDetailRepository pickUpOrderDetailRepository;

    private final PickUpOrderRepository pickUpOrderRepository;

    private final BouquetService bouquetService;

    Logger logger = LoggerFactory.getLogger(PickUpService.class);

    @Transactional
    public void pickUpCartInsert(PickUpCartRequest request, String userEmail) {
        logger.info("getFloristNumber : "+request.getFloristNumber());
        logger.info("getFlowerNumber : "+request.getFlowerNumber());
        logger.info("getFlowerCount : "+request.getFlowerCount());
        logger.info("getBouquetNumber : "+request.getBouquetNumber());
        if(request.getFlowerNumber() == 99999) {
            Optional<PickUpCartEntity> optionalPickUpCart = pickUpCartRepository
                    .findByUserEmailAndFlowerNumberAndFloristNumberAndBouquetBouquetNumber
                            (
                                    userEmail,
                                    request.getFlowerNumber(),
                                    request.getFloristNumber(),
                                    request.getBouquetNumber()
                            );

            if (optionalPickUpCart.isPresent()) {
                optionalPickUpCart.get().setFloristNumber(request.getFloristNumber());
                optionalPickUpCart.get().setFlowerNumber(request.getFlowerNumber());
                optionalPickUpCart.get().setFlowerCount(request.getFlowerCount());
                optionalPickUpCart.get().setUserEmail(userEmail);
                optionalPickUpCart.get().setBouquet(bouquetService.selelctBouquet(request.getBouquetNumber()));
                pickUpCartRepository.save(optionalPickUpCart.get());
                return;
            }

        } else {

            Optional<PickUpCartEntity> optionalPickUpCart = pickUpCartRepository
                    .findByUserEmailAndFlowerNumberAndFloristNumber
                            (
                                    userEmail,
                                    request.getFlowerNumber(),
                                    request.getFloristNumber()
                            );
            if (optionalPickUpCart.isPresent()) {
                optionalPickUpCart.get().setFloristNumber(request.getFloristNumber());
                optionalPickUpCart.get().setFlowerNumber(request.getFlowerNumber());
                optionalPickUpCart.get().setFlowerCount(request.getFlowerCount());
                optionalPickUpCart.get().setUserEmail(userEmail);
                pickUpCartRepository.save(optionalPickUpCart.get());
                return;
            }
        }

        pickUpCartRepository.save
                (
                PickUpCartEntity
                        .builder()
                        .flowerNumber(request.getFlowerNumber())
                        .floristNumber(request.getFloristNumber())
                        .flowerCount(request.getFlowerCount())
                        .userEmail(userEmail)
                        .build()
                );
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void pickUpCartDelete(String userEmail) {
        if (StringUtils.hasLength(userEmail)) {
            Optional<List<PickUpCartEntity>> pickUpCartEntityList = pickUpCartRepository.findAllByUserEmail(userEmail);
            if(pickUpCartEntityList.isPresent()) {
                pickUpCartEntityList.get().forEach(cart -> {
                    pickUpCartRepository.deleteById(cart.getPickUpCartNumber());
                });
            }
        } else throw new CustomException(ResponseCode.INVALID_REQUEST);
    }

    public void pickUpCartDeleteByPickOrderSeq(Integer orderSeq) {
        if (pickUpOrderRepository.findById(orderSeq).isPresent()) {
            Optional<List<PickUpOrderDetailEntity>> pickUpOrderDetailEntityList = pickUpOrderDetailRepository.findByPickUpOrderNumber(orderSeq);
            if (pickUpOrderDetailEntityList.isPresent()) {
                // 픽업 오더 디테일 엔티티를 이용하여 픽업 카트 삭제
                pickUpOrderDetailEntityList.get().forEach(pickUpOrderDetailEntity -> {
                    Optional<PickUpCartEntity> entity = pickUpCartRepository
                            .findByUserEmailAndFlowerNumberAndFlowerCountAndFloristNumber(
                                    pickUpOrderDetailEntity.getUserEmail(),
                                    pickUpOrderDetailEntity.getFlowerNumber(),
                                    pickUpOrderDetailEntity.getPickUpOrderDetailCount(),
                                    pickUpOrderDetailEntity.getFloristNumber());
                    if (entity.isPresent()) {
                        pickUpCartRepository.delete(entity.get());
                    }
                });
            }
        }
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void pickUpCartTargetDelete(PickUpCartRequest pick, String userEmail) {
        pickUpCartRepository.deleteByUserEmailAndAndFlowerNumberAndFloristNumber(userEmail, pick.getFlowerNumber(), pick.getFloristNumber());
    }
}
