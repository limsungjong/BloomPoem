package com.example.bloompoem.controller;

import com.example.bloompoem.domain.dto.PickUpDateAndTImeRequest;
import com.example.bloompoem.domain.kakaoPay.PayReady;
import com.example.bloompoem.service.KakaoPayService;
import com.example.bloompoem.service.OrderService;
import com.example.bloompoem.service.UserService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
public class KakaoPayController {

    Logger logger = LoggerFactory.getLogger(KakaoPayService.class);

    private final KakaoPayService kakaoPayService;

    private final UserService userService;

    private final OrderService orderService;

    @PostMapping(value = "/kakao_pay/ready")
    public @ResponseBody PayReady payReady(@RequestBody PickUpDateAndTImeRequest request
            , @CookieValue(value = "Authorization") String token
            , ModelMap model) {
        String userEmail = userService.tokenToUserEntity(token).getUserEmail();

        // 여기에서 시작하여 내부 로직에 의해 전달이 되고 난 상태
        PayReady payReady = kakaoPayService.payReady(request, userEmail);
        // 이미 외부로 전달되어 있는 상태이다.

        System.out.println(payReady.getTid());

        // 여기에선 tid를 가져오는 것이 가능
        logger.info("tid : " + payReady.getTid());

        // 이걸 째로 내보낸다.
        return payReady;
    }

    @GetMapping("/pick_up/order/pay/completed")
    public String payCompleted(@RequestParam("pg_token") String pgToken,
                               @ModelAttribute("tid") String tid,
                               @ModelAttribute("order") String order,
                               @CookieValue(value = "Authorization") String token,
                               Model model) {
        String userEmail = userService.tokenToUserEntity(token).getUserEmail();
        logger.info("결제 승인요청 토큰" + pgToken);
        logger.info("주문 정보" + order);
        logger.info("결재고유 번호" + tid);

        // 카카오 결재 요청하기
//        PayApprove payApprove = kakaoPayService.payApprove(tid, pgToken, userEmail);
        return "payCompleted";
    }

    @PostMapping("/pick_up/order/pay/pop_up")
    public String popUp(Model model , String pcUrl, String orderId , String tId, String userEmail) {
        model.addAttribute("pcUrl", pcUrl);
        model.addAttribute("orderId", orderId);
        model.addAttribute("tId", tId);
        model.addAttribute("userEmail", userEmail);

        return "payPopUp";
    }
}
