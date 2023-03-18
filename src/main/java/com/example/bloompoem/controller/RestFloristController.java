package com.example.bloompoem.controller;

import com.example.bloompoem.domain.dto.ResponseCode;
import com.example.bloompoem.entity.FloristEntity;
import com.example.bloompoem.entity.Inter.FloristFlowerInterFace;
import com.example.bloompoem.exception.CustomException;
import com.example.bloompoem.repository.FloristRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;

@RestController
@AllArgsConstructor
@CrossOrigin("http://172.28.16.1:5500")
@RequestMapping("/api/v1/florist")
public class RestFloristController {

    private final FloristRepository floristRepository;

    @GetMapping(value = "/florist_list")
    @ResponseBody
    public ResponseEntity<?> pick() {
        List<FloristEntity> arrayList;
        arrayList = floristRepository.findAll();
        return ResponseEntity.ok(arrayList);
    }

    @PostMapping(value = "/florist_list_query_x_y")
    @ResponseBody
    public ResponseEntity<?> pickQuery(@RequestParam String x, @RequestParam String y) {

        List<FloristEntity> arrayList;
        BigDecimal aadd = new BigDecimal("0.01");
        BigDecimal badd = new BigDecimal("0.005");

        BigDecimal xa = new BigDecimal(x).subtract(aadd);
        BigDecimal xb = new BigDecimal(x).add(aadd);
        System.out.println(xa);
        System.out.println(xb);

        BigDecimal ya = new BigDecimal(y).subtract(badd);
        BigDecimal yb = new BigDecimal(y).add(badd);
        System.out.println(ya);
        System.out.println(yb);
        arrayList = floristRepository.searchXY(xa, xb, ya, yb);
        return ResponseEntity.ok(arrayList);
    }

    @PostMapping(value = "/florist_product_list")
    @ResponseBody
    public ResponseEntity<?> floristList(@RequestParam Integer floristNumber) {
        if(ObjectUtils.isEmpty(floristRepository.findById(floristNumber))) throw new CustomException(ResponseCode.INVALID_REQUEST);

        List<BigInteger> arrayList;
        arrayList = floristRepository.searchFloristFlower(floristNumber);
        return ResponseEntity.ok().body(arrayList);
    }

    @PostMapping(value = "/florist_product_list_detail")
    @ResponseBody
    public ResponseEntity<List<FloristFlowerInterFace>> floristList3(@RequestParam Integer floristNumber) {
        if(floristNumber < 0) throw new CustomException(ResponseCode.INVALID_REQUEST);

        List<FloristFlowerInterFace> arrayList = floristRepository.searchFloristFlowerDetail(floristNumber);
        return ResponseEntity.ok().body(arrayList);
    }

    @PostMapping(value = "/florist_search_name")
    @ResponseBody
    public ResponseEntity<?> floristSearchName(@RequestParam String floristName) {
        if(ObjectUtils.isEmpty(floristRepository.findByFloristName(floristName))) throw new CustomException(ResponseCode.INVALID_REQUEST);

        FloristEntity floristEntity = floristRepository.findFloristEntityByFloristName(floristName);
        return ResponseEntity.ok().body(floristEntity);
    }
}
