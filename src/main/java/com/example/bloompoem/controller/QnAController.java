package com.example.bloompoem.controller;

import com.example.bloompoem.dto.QnADTO;
import com.example.bloompoem.service.QnAService;
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
    public String findAll(Model model){
        List<QnADTO> qnaDTOList = qnaService.findAll();
        model.addAttribute("QnAList", qnaDTOList);
        return "qna";
    }

}
