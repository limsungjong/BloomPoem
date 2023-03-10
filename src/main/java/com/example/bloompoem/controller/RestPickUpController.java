package com.example.bloompoem.controller;

import com.example.bloompoem.domain.dto.ResponseCode;
import com.example.bloompoem.entity.FloristEntity;
import com.example.bloompoem.entity.FlowerEntity;
import com.example.bloompoem.entity.Inter.FloristFlowerInterFace;
import com.example.bloompoem.exception.CustomException;
import com.example.bloompoem.repository.FloristProductRepository;
import com.example.bloompoem.repository.FloristRepository;
import com.example.bloompoem.repository.FlowerRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

@RestController
@AllArgsConstructor
@CrossOrigin("http://172.28.16.1:5500")
@RequestMapping("/api/v1/pick_up")
public class RestPickUpController {

    private final FloristRepository floristRepository;

    private final FlowerRepository flowerRepository;

    private final FloristProductRepository floristProductRepository;

    @GetMapping(value = "/pick_up_list")
    @ResponseBody
    public ResponseEntity<?> pick() {
        List<FloristEntity> arrayList;
        arrayList = floristRepository.findAll();
        return ResponseEntity.ok(arrayList);
    }

    @PostMapping(value = "/pick_up_list_query")
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

    @PostMapping(value = "/flower_list")
    @ResponseBody
    public ResponseEntity<?> flowerList() {
        List<FlowerEntity> arrayList;
        arrayList = flowerRepository.findAll();

        return ResponseEntity.ok(arrayList);
    }

    @PostMapping (value = "/florist")
    @ResponseBody
    public ResponseEntity<?> florist(@RequestParam Integer floristNumber) {
        if(ObjectUtils.isEmpty(floristNumber)) {
            throw new CustomException(ResponseCode.INVALID_REQUEST);
        }
        floristRepository.findById(floristNumber);

        return ResponseEntity.ok("정상!");
    }

    @PostMapping(value = "/florist_product_list")
    @ResponseBody
    public ResponseEntity<?> floristList(@RequestParam Long floristNumber) {
        List<BigInteger> arrayList;
        arrayList = floristRepository.searchFloristFlower(floristNumber);

        return ResponseEntity.ok().body(arrayList);
    }

    @PostMapping(value = "/florist_product_list_detail")
    @ResponseBody
    public ResponseEntity<List<FloristFlowerInterFace>> floristList3(@RequestParam Long floristNumber) {
        List<FloristFlowerInterFace> arrayList = floristRepository.searchFloristFlower3(floristNumber);
        List<FlowerEntity> flowerList = new ArrayList<>();
        return ResponseEntity.ok().body(arrayList);
    }

    @PostMapping(value = "/florist_search_name")
    @ResponseBody
    public ResponseEntity<?> floristSearchName(@RequestParam String floristName) {
        System.out.println(floristName);
        FloristEntity floristEntity = floristRepository.findFloristEntityByFloristName(floristName);
        return ResponseEntity.ok().body(floristEntity);
    }


}
