package com.example.bloompoem.service;

import com.example.bloompoem.domain.dto.ResponseCode;
import com.example.bloompoem.entity.FloristReviewEntity;
import com.example.bloompoem.exception.CustomException;
import com.example.bloompoem.repository.FloristReviewRepository;
import com.example.bloompoem.repository.PickUpOrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PickUpOrderReviewService {

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
}
