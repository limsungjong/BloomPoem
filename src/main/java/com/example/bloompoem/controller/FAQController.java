package com.example.bloompoem.controller;

import com.example.bloompoem.entity.FAQEntity;
import com.example.bloompoem.service.FAQService;
import com.example.bloompoem.service.UserService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@RequiredArgsConstructor
@Controller
public class FAQController {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final FAQService faqService;

    // FAQ
    @GetMapping("/FAQ")
    public String getFAQList(Model model, Pageable pageable){
        Page<FAQEntity> faqEntityPage = faqService.getFAQList(pageable);
        model.addAttribute("FAQList", faqEntityPage);
        return "/faq";
    }

}
