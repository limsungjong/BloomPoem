package com.example.bloompoem.service;

import com.example.bloompoem.repository.FloristReviewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PickUpOrderReviewService {

    private final FloristReviewRepository floristReviewRepository;

    public boolean checkPickUpOrderReview(Integer pickUpOrderNumber) {
        return floristReviewRepository.existsById(pickUpOrderNumber);
    }
}
