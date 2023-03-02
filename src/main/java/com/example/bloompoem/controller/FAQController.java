package com.example.bloompoem.controller;

import com.example.bloompoem.dto.FAQDTO;
import com.example.bloompoem.service.FAQService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;


@RequiredArgsConstructor
@Controller
public class FAQController {

    @Autowired
    FAQService faqService;

    // FAQ
    @GetMapping("/FAQ")
    public String findAll(Model model){
        List<FAQDTO> faqDTOList = faqService.findAll();
        model.addAttribute("FAQList", faqDTOList);
        return "faq";
    }
}
