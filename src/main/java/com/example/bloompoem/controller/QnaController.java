package com.example.bloompoem.controller;

import com.example.bloompoem.entity.QnaEntity;
import com.example.bloompoem.repository.QnaRepository;
import com.example.bloompoem.service.QnaService;
import com.example.bloompoem.service.UserService;
import com.example.bloompoem.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.query.Param;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import java.io.File;
import java.time.LocalDateTime;
import java.util.UUID;

@Controller
@RequiredArgsConstructor
public class QnaController {

    @Value("#{environment['jwt.secret']}")
    private String secretKey;
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private final QnaService qnaService;
    private final UserService userService;

    @Value("#{environment['file.path']}")
    private String FILE_PATH;
    // 나의 문의 내역 리스트
    @GetMapping("/qna")
    public String getQnaList(Model model, Pageable pageable,@CookieValue(value = "Authorization") String token ){
        String userEmail = JwtUtil.getUserName(token,secretKey);
        model.addAttribute("qnaList", qnaService.getQnaList(pageable,userEmail));

        return "/CS/qna";
    }

    // 문의 글쓰기 페이지
    @GetMapping("/qna/write")
    public String writeForm(Model model, @CookieValue(value = "Authorization", required = false) String token){

        if(token == null) return "/signIn";

        String userEmail = JwtUtil.getUserName(token,secretKey);
        model.addAttribute("userEmail", userEmail);
        logger.error("userEmail : " + userEmail);

        return "/CS/qnaWrite";
    }

    // 문의 글쓰기
    @PostMapping("/qna/write")
    public String write(@RequestParam(required = false) MultipartFile qnaImage, String userEmail, String qnaTitle, String qnaStatus, String qnaContent){
        QnaEntity qna =new QnaEntity();
        if(!qnaImage.isEmpty()){
            String imageName = UUID.randomUUID().toString().replace("-", "") + ".jpg";
            File imageFile = new File(FILE_PATH, imageName);
            try {
                qnaImage.transferTo(imageFile);
            } catch (Exception e) {
                logger.error("[insertReview] Error : " + e);
            }
            qna.setQnaImage1(imageName);
        }

        qna.setUserEmail(userEmail);
        qna.setQnaDate(LocalDateTime.now());
        qna.setQnaStatus('N');
        qna.setQnaTitle(qnaTitle);
        qna.setQnaGroup(0);
        qna.setQnaIndent(0);
        qna.setQnaParent(0);
        qna.setQnaOrder(0);
        qna.setQnaContent(qnaContent);

        qnaService.write(qna);
        return "redirect:/qna";
    }

    // 문의 글보기 페이지
    @GetMapping("/qna/view")
    public String view(Model model, Integer qnaNumber){
        QnaEntity qnaEntity= qnaService.findById(qnaNumber);
        model.addAttribute("qna", qnaEntity);
        return "/CS/qnaView";
    }

    // 문의 글수정 페이지
    @GetMapping("/qna/update")
    public String updateForm(Model model, @ModelAttribute QnaEntity qnaEntity, @CookieValue(value = "Authorization", required = false) String token){
        if(token == null) return "/signIn";
        String userEmail = JwtUtil.getUserName(token,secretKey);
        QnaEntity qna = qnaService.findById(qnaEntity.getQnaNumber());

        model.addAttribute("qna", qna);

        return "/CS/qnaUpdate";
    }

    // 문의 글수정
    @PostMapping("/qna/update")
    public String update(Model model, @ModelAttribute QnaEntity qnaEntity, @CookieValue(value = "Authorization", required = false) String token){
        String userEmail = userService.tokenToUserEntity(token).getUserEmail();
        model.addAttribute("userEmail", userEmail);
        QnaEntity qnaNumber = qnaService.findById(qnaEntity.getQnaNumber());
        qnaNumber.setQnaTitle(qnaEntity.getQnaTitle());
        qnaNumber.setQnaContent(qnaEntity.getQnaContent());
        qnaService.write(qnaNumber);

        return "redirect:/qna";
    }

    // 문의 글삭제
    @GetMapping("/qna/delete")
    public String delete(Integer qnaNumber ) {
        qnaService.deleteById(qnaNumber);
        return "redirect:/qna";
    }

    // 답글 쓰기 페이지
    // 들여쓰기는 최대 3번 까지(답글 3회)
    @GetMapping("/qna/reply")
    public String replyForm(Model model, Integer qnaNumber,  @CookieValue(value = "Authorization", required = false) String token) {
        String userEmail = userService.tokenToUserEntity(token).getUserEmail();
        QnaEntity qna = qnaService.findById(qnaNumber);
        model.addAttribute("qna", qna);
        model.addAttribute("userEmail", userEmail);
        return  "/CS/qnaReply";
    }

    // 답글 쓰기
    @PostMapping("/qna/reply")
    public String reply(@ModelAttribute QnaEntity qnaEntity, int beforeQnaNumber){
        QnaEntity qna = qnaService.findById(beforeQnaNumber);
        qna.setQnaStatus('Y');
        qnaService.update(qna);
        //기존 qnaStatus 변경
        qnaEntity.setQnaDate(LocalDateTime.now());
        qnaEntity.setQnaStatus('R');
        qnaEntity.setQnaGroup(qna.getQnaGroup());
        qnaEntity.setQnaParent(beforeQnaNumber);
        qnaEntity.setQnaOrder(qna.getQnaOrder()+1);
        qnaEntity.setQnaIndent(qna.getQnaIndent()+1);
        //qnaOrder 증가하는 service 시작
        qnaService.qnaOrder(qna.getQnaGroup(), qna.getQnaOrder());

        //저장
        qnaService.qnaReply(qnaEntity);
        return "redirect:/qna";
    }
}
