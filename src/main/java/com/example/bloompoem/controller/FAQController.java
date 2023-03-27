package com.example.bloompoem.controller;


import com.example.bloompoem.entity.FAQEntity;
import com.example.bloompoem.service.FAQService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class FAQController {

    @Autowired
    private FAQService faqService;

    @GetMapping("/FAQ")
    public String faqView(Model model, @PageableDefault(size = 4) Pageable pageable){
        model.addAttribute("faq",faqService.getFAQList(pageable));


        return "CS/FAQ";
    }


}
