package com.example.bloompoem.controller;

import com.example.bloompoem.entity.QnaEntity;
import com.example.bloompoem.repository.QnaRepository;
import com.example.bloompoem.service.QnaService;
import com.example.bloompoem.service.UserService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@Controller
@RequiredArgsConstructor
public class QnaController {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final QnaService qnaService;

    private final QnaRepository qnaRepository;

    private final UserService userService;

    // 나의 문의 내역 리스트
    @GetMapping("/qna")
    public String getQnaList(Model model, @PageableDefault(page = 0, size = 7, sort = "qnaNumber", direction = Sort.Direction.DESC)
    Pageable pageable, @CookieValue(value = "Authorization", required = false) String token){

        if(token == null) return "/signIn";
        userService.tokenToUserEntity(token).getUserEmail();

        Page<QnaEntity> qnaEntityPage = qnaService.getQnaList(pageable);
        int nowPage = pageable.getPageNumber() + 1;
        int startPage = Math.max(nowPage - 4, 1);
        int endPage = Math.min(nowPage + 5, qnaEntityPage.getTotalPages());

        model.addAttribute("QnaList", qnaEntityPage);
        model.addAttribute("nowPage", nowPage);
        model.addAttribute("startPage", startPage);
        model.addAttribute("endPage", endPage);

        logger.error("startPage : " + startPage);
        logger.error("endPage : " + endPage);

        return "/qna";
    }

    // 문의 글쓰기 페이지
    @GetMapping("/qna/write")
    public String writeForm(Model model, @CookieValue(value = "Authorization", required = false) String token){

        if(token == null) return "/signIn";

        String userEmail = userService.tokenToUserEntity(token).getUserEmail();
        model.addAttribute("userEmail", userEmail);
        logger.error("userEmail : " + userEmail);

        return "/qnaWrite";
    }

    // 문의 글쓰기
    @PostMapping("/qna/write")
    public String write(@ModelAttribute QnaEntity qnaEntity){

        // 답글이 아닌 경우(부모 게시글이 존재하지 않는 경우)
        qnaEntity.setQnaDate(LocalDateTime.now());
        qnaEntity.setQnaStatus('N');
        qnaEntity.setQnaGroup(0);
        qnaEntity.setQnaIndent(0);
        qnaEntity.setQnaParent(0);
        qnaEntity.setQnaOrder(0);
        qnaEntity.setQnaImage1("");
        qnaEntity.setQnaImage2("");
        qnaEntity.setQnaImage3("");
        System.out.println("QnaEntity = " + qnaEntity);

        qnaService.write(qnaEntity);
        return "redirect:/qna";
    }

    // 문의 글보기 페이지
    @GetMapping("/qna/view")
    public String view(Model model, Integer qnaNumber){
        QnaEntity qnaEntity= qnaService.findById(qnaNumber);
        logger.debug("" + qnaEntity);
        model.addAttribute("qna", qnaEntity);

        return "/qnaView";
    }

    // 문의 글수정 페이지
    @GetMapping("/qna/update")
    public String updateForm(Model model, @ModelAttribute QnaEntity qnaEntity,
    @CookieValue(value = "Authorization", required = false) String token){

        if(token == null) return "/signIn";

        String userEmail = userService.tokenToUserEntity(token).getUserEmail();
        model.addAttribute("userEmail", userEmail);

        QnaEntity qnaNumber = qnaService.findById(qnaEntity.getQnaNumber());
        model.addAttribute("qna", qnaNumber);

//        logger.error("qnaContent : " + qnaNumber.getQnaContent());

        return "/qnaUpdate";
    }

    // 문의 글수정
    @PostMapping("/qna/update")
    public String update(Model model, @ModelAttribute QnaEntity qnaEntity,
    @CookieValue(value = "Authorization", required = false) String token){

        String userEmail = userService.tokenToUserEntity(token).getUserEmail();
        model.addAttribute("userEmail", userEmail);

        QnaEntity qnaNumber = qnaService.findById(qnaEntity.getQnaNumber());
//        model.addAttribute("qna", qnaNumber);

//        logger.error("qnaNumber : " + qnaNumber);
//        logger.error("" + qnaNumber.getQnaDate());
//        logger.error(""+ qnaEntity.getQnaDate());
//        qnaEntity.getQnaDate();
        qnaNumber.setQnaTitle(qnaEntity.getQnaTitle());
        qnaNumber.setQnaContent(qnaEntity.getQnaContent());

        qnaService.write(qnaNumber);

//        logger.error("userEmail : " + userEmail);
//        logger.error("qnaTitle : " + qnaDTO.getQnaTitle());
//        logger.error("qnaContent : " + qnaDTO.getQnaContent());
//        logger.error("write : " + qnaEntity);
//        logger.error(write(qnaEntity));

        return "redirect:/qna";
    }

    // 문의 글삭제
    @GetMapping("/qna/delete")
    public String delete(Integer qnaNumber) {
        qnaService.deleteById(qnaNumber);
        return "redirect:/qna";
    }

    // 답글 쓰기 페이지
    // 들여쓰기는 최대 3번 까지(답글 3회)
//    @GetMapping("/qna/reply")
//    public String replyForm() { return  "/qnaReply"; }

    // 답글 쓰기
//    @PostMapping("/qna/reply")
//    public String reply(@ModelAttribute QnaEntity qnaEntity){
        // 답글인 경우(부모 게시글이 존재하는 경우)
//        return "redirect:/qna";
//    }
}
