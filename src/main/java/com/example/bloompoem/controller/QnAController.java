package com.example.bloompoem.controller;

import com.example.bloompoem.entity.QnAEntity;
import com.example.bloompoem.service.QnAService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class QnAController {
    @Autowired
    QnAService qnaService;

    @GetMapping("/QnA")
    public String getQnAList(Model model, Pageable pageable){
        Page<QnAEntity> qnAEntityPage = qnaService.getQnAList(pageable);
        model.addAttribute("QnAList", qnAEntityPage);
        return "qna";
    }

}
