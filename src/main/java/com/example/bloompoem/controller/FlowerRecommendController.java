package com.example.bloompoem.controller;

import com.example.bloompoem.entity.FlowerEntity;
import com.example.bloompoem.service.FlowerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class FlowerRecommendController {
    @Autowired
    private FlowerService flowerService;


    @GetMapping("/recommend")
    public String recommendGo(Model model, @PageableDefault(size = 12)Pageable pageable, @RequestParam(required = false, defaultValue = "0") int page){
        Page<FlowerEntity> flower =flowerService.findFlowerAll(pageable);

        model.addAttribute("page", page);
        model.addAttribute("flower",flower);
        return "/flowerRecommend";
    }
    @GetMapping("/recommend/search_tag")
    public ResponseEntity<Page<FlowerEntity>> searchTag (String tag ,@PageableDefault(size = 12) Pageable pageable){
        return  ResponseEntity.ok(flowerService.searchTag(tag,pageable));
    }
}
