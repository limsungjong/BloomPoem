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

import java.time.LocalDate;
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
        int order = qnaEntity.getQnaOrder();

        qnaEntity.setQnaDate(LocalDateTime.now());

        // 답글이 아닌 경우(부모 게시글이 존재하지 않는 경우)
        if(reply == 'N'){
            qnaEntity.setQnaStatus('N');
            qnaEntity.setQnaGroup(0);
            qnaEntity.setQnaIndent(0);
            qnaEntity.setQnaParent(0);
            qnaEntity.setQnaOrder(0);
        }
        // 답글인 경우(부모 게시글이 존재하는 경우)
        else if(reply == 'Y'){
            qnaEntity.setQnaStatus('Y');
            qnaEntity.setQnaGroup(qnaEntity.getQnaGroup());   //부모 게시글의 그룹
            qnaEntity.setQnaIndent(qnaEntity.getQnaIndent() + 1);  //부모의 Indent + 1
            qnaEntity.setQnaParent(qnaEntity.getQnaNumber());  //부모 게시글의 시퀀스(넘버)
            qnaEntity.setQnaOrder(qnaEntity.getQnaOrder() + 1);
            // Order 값 증가의 분기 기준은 작성시간인가??
            // 근데 자신에게 답글이 달리면 Order값이 증가하지 않음...

            // 기존 게시글의 Order 값을 +1 시키고 새로 들어온 게시글은??
            // 기존의 Order값을 가지고 새로 들어온다... (어떻게?? insert??)

            // 날짜는 크고 작은게 아니라 선후관계 비교인데... 정렬??

            // Order 값이 +1될때(나중에 작성된 댓글 or 대댓글)

//            if(order > ){
////                for(){}
//                qnaEntity.setQnaOrder(qnaEntity.getQnaOrder() + 1);
//            }
//            // Order 값이 그대로일때(먼저 작성된 댓글 or 대댓글)
//            else if(order ) {
//                qnaEntity.setQnaOrder(qnaEntity.getQnaOrder());
//            }

            // 들여쓰기는 최대 3번 까지(답글 3회)
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
