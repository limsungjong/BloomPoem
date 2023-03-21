package com.example.bloompoem.controller;

import com.example.bloompoem.domain.dto.FloristAndReviewResponse;
import com.example.bloompoem.domain.dto.ResponseCode;
import com.example.bloompoem.entity.FloristEntity;
import com.example.bloompoem.entity.Inter.FloristAndReviewScore;
import com.example.bloompoem.entity.Inter.FloristFlowerInterFace;
import com.example.bloompoem.exception.CustomException;
import com.example.bloompoem.repository.FloristRepository;
import com.example.bloompoem.repository.FloristReviewRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

@RestController
@AllArgsConstructor
@CrossOrigin("http://172.28.16.1:5500")
@RequestMapping("/api/v1/florist")
public class RestFloristController {

    private final FloristRepository floristRepository;

    private final FloristReviewRepository floristReviewRepository;

    @GetMapping(value = "/florist_list")
    @ResponseBody
    public ResponseEntity<?> pick() {
        List<FloristAndReviewScore> floristListAndReviewScore = floristRepository.getFloristListAndReviewScore();
        List<FloristAndReviewResponse> list = new ArrayList<>();
        floristListAndReviewScore.forEach(florist -> {
            list.add(FloristAndReviewResponse
                    .builder()
                    .userEmail(florist.getUserEmail())
                    .floristPhoneNumber(florist.getFloristPhoneNumber())
                    .floristNumber(florist.getFloristNumber())
                    .floristAddress(florist.getFloristAddress())
                    .floristLatitude(florist.getFloristLatitude())
                    .floristLongtitude(florist.getFloristLongtitude())
                    .floristName(florist.getFloristName())
                    .floristReviewScore(florist.getFloristReviewScore())
                    .floristReviewCount(florist.getFloristReviewCount())
                    .build());
        });
        return ResponseEntity.ok().body(list);
    }

    @PostMapping(value = "/florist_list_query_x_y")
    @ResponseBody
    public ResponseEntity<?> pickQuery(@RequestParam String x, @RequestParam String y) {

        List<FloristAndReviewScore> arrayList;
        BigDecimal aadd = new BigDecimal("0.01");
        BigDecimal badd = new BigDecimal("0.005");

        BigDecimal xa = new BigDecimal(x).subtract(aadd);
        BigDecimal xb = new BigDecimal(x).add(aadd);

        BigDecimal ya = new BigDecimal(y).subtract(badd);
        BigDecimal yb = new BigDecimal(y).add(badd);
        arrayList = floristRepository.searchXY2(xa, xb, ya, yb);
        return ResponseEntity.ok(arrayList);
    }

    @PostMapping(value = "/florist_product_list")
    @ResponseBody
    public ResponseEntity<?> floristList(@RequestParam Integer floristNumber) {
        if (ObjectUtils.isEmpty(floristRepository.findById(floristNumber)))
            throw new CustomException(ResponseCode.INVALID_REQUEST);

        List<BigInteger> arrayList;
        arrayList = floristRepository.searchFloristFlower(floristNumber);
        return ResponseEntity.ok().body(arrayList);
    }

    @PostMapping(value = "/florist_product_list_detail")
    @ResponseBody
    public ResponseEntity<List<FloristFlowerInterFace>> floristList3(@RequestParam Integer floristNumber) {
        if (floristNumber < 0) throw new CustomException(ResponseCode.INVALID_REQUEST);

        List<FloristFlowerInterFace> arrayList = floristRepository.searchFloristFlowerDetail(floristNumber);
        return ResponseEntity.ok().body(arrayList);
    }

    @PostMapping(value = "/florist_search_name")
    @ResponseBody
    public ResponseEntity<?> floristSearchName(@RequestParam String floristName) {
        if (ObjectUtils.isEmpty(floristRepository.findByFloristName(floristName)))
            throw new CustomException(ResponseCode.INVALID_REQUEST);

        FloristEntity floristEntity = floristRepository.findFloristEntityByFloristName(floristName);
        return ResponseEntity.ok().body(floristEntity);
    }

    @PostMapping(value = "/florist_review")
    @ResponseBody
    public ResponseEntity<?> getFloristReview(@RequestParam int floristNumber) {
        return ResponseEntity.ok().body(floristReviewRepository.findAllByFloristNumber(floristNumber));
    }
}
