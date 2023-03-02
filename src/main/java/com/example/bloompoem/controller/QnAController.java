package com.example.bloompoem.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class QnAController {
    @Autowired
    QnAService qnaService;

    @GetMapping("/QnA")
    public String findAllQnAList(Model model){
        List<QnADTO> qnaDTOList = qnaService.findAllQnAList();
        model.addAttribute("QnAList", qnaDTOList);
        return "qna";
    }

}
