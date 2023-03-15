package com.example.bloompoem.controller;

import com.example.bloompoem.dto.QnaDTO;
import com.example.bloompoem.entity.QnaEntity;
import com.example.bloompoem.repository.QnaRepository;
import com.example.bloompoem.service.QnaService;
import com.example.bloompoem.util.FileUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Date;

@Controller
@RequiredArgsConstructor
public class QnaController {

    private final QnaService qnaService;

    private final QnaRepository qnaRepository;

    // 나의 문의 내역 리스트
    @GetMapping("/qna")
    public String getQnaList(Model model, Pageable pageable){
        Page<QnaEntity> qnaEntityPage = qnaService.getQnaList(pageable);
        model.addAttribute("QnaList", qnaEntityPage);
        return "/qna";
    }

    // 문의 글쓰기 페이지
    @GetMapping("/qna/write")
    public String writeForm(){ return "/qnaWrite"; }

    // 답글 쓰기 페이지
    @GetMapping("/qna/reply")
    public String replyForm() { return  "/qnaReply"; }

    // 문의 글쓰기
    @PostMapping("/qna/write")
    public String write(@ModelAttribute QnaEntity qnaEntity){
        char reply = qnaEntity.getQnaStatus();

        qnaEntity.setQnaDate(LocalDateTime.now());

        // 답글이 없는 경우
        if(reply == 'N'){
            qnaEntity.setQnaStatus('N');
            qnaEntity.setQnaGroup(0);
            qnaEntity.setQnaIndent(0);
            qnaEntity.setQnaParent(0);
            qnaEntity.setQnaOrder(0);
        }
        // 답글이 있는 경우
        else if(reply == 'Y'){
            qnaEntity.setQnaStatus('Y');
            qnaEntity.setQnaGroup(0);   //부모 게시글의 그룹
            qnaEntity.setQnaIndent(qnaEntity.getQnaIndent() + 1);  //부모의 Indent + 1
            qnaEntity.setQnaParent(qnaEntity.getQnaNumber());  //부모 게시글의 시퀀스(넘버)

//            if(){
                qnaEntity.setQnaOrder(0);
//            }

        }

        qnaEntity.setQnaImage1("");
        qnaEntity.setQnaImage2("");
        qnaEntity.setQnaImage3("");
        System.out.println("QnaDTO = " + qnaEntity);

        qnaService.write(qnaEntity);
        return "/qna";
    }

    // 문의 글 보기
    @GetMapping("/qna/view")
    public String view(){ return "/qnaView"; }

}
