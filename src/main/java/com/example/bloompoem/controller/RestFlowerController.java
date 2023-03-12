package com.example.bloompoem.controller;

import com.example.bloompoem.domain.dto.ResponseCode;
import com.example.bloompoem.entity.FlowerEntity;
import com.example.bloompoem.exception.CustomException;
import com.example.bloompoem.repository.FlowerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;


@RequestMapping(value = "/api/v1/flower")
@RequiredArgsConstructor
@RestController
@CrossOrigin(value = "http://192.168.45.66:5500")
public class RestFlowerController {

    private final FlowerRepository flowerRepository;

    @PostMapping(value = "/flower_list")
    @ResponseBody
    public ResponseEntity<?> flowerList(Integer flowerNumber) {
        Optional<FlowerEntity> flowerEntity = flowerRepository.findById(flowerNumber);
        if(flowerEntity.isPresent()) {
        return ResponseEntity.ok(flowerEntity.get());

        }
        throw new CustomException(ResponseCode.INVALID_REQUEST);
    }
}
