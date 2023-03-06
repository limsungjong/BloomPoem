package com.example.bloompoem.controller;

import com.example.bloompoem.dto.QnADTO;
import com.example.bloompoem.entity.QnAEntity;
import com.example.bloompoem.service.QnAService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class QnAController {
    @Autowired
    QnAService qnaService;

    // 나의 문의 내역 리스트
    @GetMapping("/QnA")
    public String getQnAList(Model model, Pageable pageable){
        Page<QnAEntity> qnAEntityPage = qnaService.getQnAList(pageable);
        model.addAttribute("QnAList", qnAEntityPage);
        return "/QnA";
    }

    // 문의 글쓰기
    @GetMapping("/QnA/write")
    public String write(Model model){
        return "/QnA/write";
    }

    @PostMapping("QnA/write")
    public String write(@ModelAttribute QnADTO qnaDTO){
        qnaService.write(qnaDTO);

        return "QnA/write";
    }


    // 문의 글 보기
    @GetMapping("/QnA/view")
    public String view(Model model){
        return "/QnA/view";
    }
}
