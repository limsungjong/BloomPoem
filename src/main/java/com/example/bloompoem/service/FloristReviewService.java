package com.example.bloompoem.service;

import com.example.bloompoem.domain.dto.ResponseCode;
import com.example.bloompoem.entity.FloristReviewEntity;
import com.example.bloompoem.exception.CustomException;
import com.example.bloompoem.repository.FloristReviewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class FloristReviewService {

    private final FloristReviewRepository floristReviewRepository;

    public boolean checkPickUpOrderReview(Integer pickUpOrderNumber) {
        return floristReviewRepository.existsByPickUpOrderNumber(pickUpOrderNumber);
    }

    public FloristReviewEntity saveOrderReview(int floristNumber,
                                String userEmail,
                                int pickUpOrderNumber,
                                String pickUpOrderContent,
                                char pickUpOrderScore) {
        if(checkPickUpOrderReview(pickUpOrderNumber)) {
            throw new CustomException(ResponseCode.INVALID_REQUEST);
        }
        FloristReviewEntity floristReviewEntity = FloristReviewEntity
                .builder()
                .userEmail(userEmail)
                .pickUpOrderNumber(pickUpOrderNumber)
                .floristNumber(floristNumber)
                .floristReviewContent(pickUpOrderContent)
                .floristReviewScore(pickUpOrderScore)
                .floristReviewRegDate(LocalDate.now())
                .build();
        floristReviewRepository.save(floristReviewEntity);
        return floristReviewEntity;
    }

    public void saveOrderReviewImage(Integer seq, String imgName) {
        if(floristReviewRepository.existsById(seq)) {
            FloristReviewEntity entity = floristReviewRepository.findById(seq).get();
            entity.setFloristReviewImage(imgName);
            floristReviewRepository.save(entity);
        }
    }

    public Page<FloristReviewEntity> floristReviewView (String userEmail, Pageable pageable){
        return floristReviewRepository.findByUserEmailOrderByFloristReviewRegDateDesc(userEmail, pageable);
    }

    public void floristReviewUpdate(
            int orderReviewNumber,
            String pickUpOrderContent,
            char pickUpOrderScore
    ) {
        FloristReviewEntity floristReviewEntity = floristReviewRepository.findById(orderReviewNumber).get();
        FloristReviewEntity insertReview = FloristReviewEntity
                .builder()
                .floristReviewImage(floristReviewEntity.getFloristReviewImage())
                .floristReviewNumber(floristReviewEntity.getFloristReviewNumber())
                .userEmail(floristReviewEntity.getUserEmail())
                .pickUpOrderNumber(floristReviewEntity.getPickUpOrderNumber())
                .floristReviewRegDate(LocalDate.now())
                .floristReviewContent(pickUpOrderContent)
                .floristReviewScore(pickUpOrderScore)
                .floristNumber(floristReviewEntity.getFloristNumber())
                .build();
        floristReviewRepository.save(insertReview);
    }

    public void floristReviewDelete(int orderReviewNumber) {
        floristReviewRepository.deleteById(orderReviewNumber);
    }
}
