package com.example.bloompoem.controller;

import com.example.bloompoem.dto.QnADTO;
import com.example.bloompoem.entity.QnAEntity;
import com.example.bloompoem.service.QnAService;
import com.example.bloompoem.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class QnAController {
    @Autowired
    QnAService qnaService;

    @Autowired
    UserService userService;

    // 나의 문의 내역 리스트
    @GetMapping("/QnA")
    public String getQnAList(Model model, Pageable pageable){
        Page<QnAEntity> qnaEntityPage = qnaService.getQnAList(pageable);

        model.addAttribute("QnAList", qnaEntityPage);
        return "/QnA";
    }

    // 문의글 화면
    @GetMapping("/QnA/write")
    public String write(Model model){
        return "/QnAwrite";
    }

    // 문의글 저장
    @PostMapping("/QnA/write")
    public String save(@ModelAttribute QnADTO qnaDTO){
        System.out.println(qnaDTO.getQnaTitle());

        //qnaService.save(qnaDTO);

        return "/QnA";
        // return "redirect:/QnA/view"; (아직 만들기 전)
    }

    // 문의글 보기
    @GetMapping("/QnA/view")
    public String view(Model model){
        return "/QnA/view";
    }

    // 문의글 삭제
//    @GetMapping("/QnA/delete")
//    public String delete
}
