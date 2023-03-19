package com.example.bloompoem.controller;

import com.example.bloompoem.domain.dto.PickUpCartRequest;
import com.example.bloompoem.domain.dto.PickUpDateAndTImeRequest;
import com.example.bloompoem.domain.kakaoPay.PayOrderProduct;
import com.example.bloompoem.domain.kakaoPay.PayReady;
import com.example.bloompoem.dto.KakaoApprovar;
import com.example.bloompoem.entity.Inter.PickUpOrderResponse;
import com.example.bloompoem.repository.PickUpOrderRepository;
import com.example.bloompoem.service.KakaoPayService;
import com.example.bloompoem.service.OrderService;
import com.example.bloompoem.service.PickUpService;
import com.example.bloompoem.service.UserService;
import lombok.RequiredArgsConstructor;
import net.minidev.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
@CrossOrigin(value = "http://192.168.45.17:5500")
public class KakaoPayController {

    Logger logger = LoggerFactory.getLogger(KakaoPayService.class);

    private final KakaoPayService kakaoPayService;

    private final UserService userService;

    private final OrderService orderService;

    private final PickUpService pickUpService;

    private final PickUpOrderRepository pickUpOrderRepository;


    // 결제 요청 1단계를 마친다.
    @PostMapping(value = "/kakao_pay/ready")
    public @ResponseBody ResponseEntity<JSONObject> payReady(
            @RequestBody PickUpDateAndTImeRequest request,
            @CookieValue(value = "Authorization") String token) {
        String userEmail = userService.tokenToUserEntity(token).getUserEmail();

        Integer totalAmount = 0;
        // 물품 총액을 계산
        for (PayOrderProduct product : request.getOrderList()) {
            totalAmount += (product.getFloristProductPrice() * product.getFlowerCount());
        }

        // 오더 테이블과 디테일 모두 저장하고 난 후 seq를 가져온다.
        Integer orderSeq = orderService.detailSaveOrder(request, userEmail, totalAmount);

        // 카트에 모두 추가한다.
        request.getOrderList().forEach(data -> {
            pickUpService.pickUpCartInsert(PickUpCartRequest
                    .builder()
                    .floristNumber(data.getFloristNumber())
                    .flowerNumber(data.getFlowerNumber())
                    .flowerCount(data.getFlowerCount())
                    .build(), userEmail);
        });

        // 여기에서 시작하여 내부 로직에 의해 전달이 되고 난 상태
        PayReady payReady = kakaoPayService.payReady(request, totalAmount, orderSeq, userEmail);
        logger.error("" + payReady);
        // 이미 외부로 전달되어 있는 상태이다.

        // 클라이언트로 전달하는데 json형태로 전달.
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("userEmail", userEmail);
        jsonObject.put("orderId", String.valueOf(orderSeq));
        jsonObject.put("tId", payReady.getTid());
        jsonObject.put("pcUrl", payReady.getNext_redirect_pc_url());

        // 처음 호출한 곳인 모달 창으로 다시 내보낸다. pickUp.js
        return ResponseEntity.ok(jsonObject);
    }

    @PostMapping("/pick_up/order/pay/pop_up")
    // js 함수에 의해 실행되며 result에 담긴 3가지 변수를 받는다
    public String popUp(Model model, String pcUrl, String orderId, String tId, String userEmail) {
        // 팝업 창에 의해 값을 전달 받는다.
        logger.info(pcUrl);
        logger.info(orderId);
        logger.info(tId);
        logger.info(userEmail);

        // model에 담아서 들어온 그대로 다시 내보낸다.
        // 때문에 여기서 받는 값은 타임리프로 사용해야 한다.
        model.addAttribute("pcUrl", pcUrl);
        model.addAttribute("orderId", orderId);
        model.addAttribute("tId", tId);
        model.addAttribute("userEmail", userEmail);

        // post를 호출한 팝업창에 내보낸다.
        return "/payPickUp/payPopUp";
    }

    // 결제 요청 승인 요청
    @PostMapping("/pick_up/order/pay/completed")
    public String payCompleted(Model model
            , String orderId
            , String tId
            , String pgToken
            , String cookie) {
        String userEmail = userService.tokenToUserEntity(cookie).getUserEmail();
        // 카카오 결제 요청하기
        KakaoApprovar payApprove = kakaoPayService.payApprove(tId, pgToken, orderId, userEmail);
        if(payApprove != null) {
            logger.info("payApprove 들어옴!");
            model.addAttribute("payApprove",payApprove);

            List<PickUpOrderResponse> responseList = pickUpOrderRepository
                    .searchPickUpOrderResponse(userEmail, Integer.valueOf(orderId));
            model.addAttribute("resDetail",responseList);
        }

        return "/payPickUp/payCompleted";
    }

    // 결제 페이지 요청
    @GetMapping("/pick_up/order/pay/completed")
    public String payResult(Model model,
                            String pg_token) {
        model.addAttribute("pgToken", pg_token);

        return "/payPickUp/payResult";
    }

    // 결제 페이지에서 오더 아이디를 다시 채워서 payApprove 페이지로 보냄
    @PostMapping("/pick_up/order/pay/success")
    public String paymentSuccess (Model model,
                                  int orderId,
                                  @CookieValue(value = "Authorization") String token){
        userService.tokenToUserEntity(token);
        model.addAttribute("orderId", orderId);
        return "/payPickUp/payApprove";
    }
}
