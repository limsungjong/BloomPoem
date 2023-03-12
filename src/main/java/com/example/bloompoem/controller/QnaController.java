package com.example.bloompoem.controller;

import com.example.bloompoem.dto.QnaDTO;
import com.example.bloompoem.entity.QnaEntity;
import com.example.bloompoem.service.QnaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class QnaController {
    @Autowired
    QnaService qnaService;

    // 나의 문의 내역 리스트
    @GetMapping("/qna")
    public String getQnaList(Model model, Pageable pageable){
        Page<QnaEntity> qnaEntityPage = qnaService.getQnaList(pageable);
        model.addAttribute("QnaList", qnaEntityPage);
        return "/qna";
    }

    // 문의 글쓰기
    @GetMapping("/qna/write")
    public String write(Model model){
        return "/qnaWrite";
    }

    @PostMapping("/qna/write")
    public String write(@ModelAttribute QnaDTO qnaDTO){
        qnaService.write(qnaDTO);

        return "/qnaWrite";
    }

    //


    // 문의 글 보기
    @GetMapping("/qna/view")
    public String view(Model model){
        return "/qnaView";
    }
}
