package com.example.bloompoem.controller;

import com.example.bloompoem.repository.FloristRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;


@Controller
@RequiredArgsConstructor
public class PickUpController {
    FloristRepository floristRepository;
    @GetMapping(value = "/pick_up")
    public String pickUpPage() {
        return "/pickUp";
    }

//    @PostMapping(value = "/pick_up_list")
//    public ResponseEntity<List<FloristDTO>> pick() {
//        List<FloristDTO> arrayList = new ArrayList<>();
//
//
//        return ResponseEntity.ok().body("성공");
//    }
}
