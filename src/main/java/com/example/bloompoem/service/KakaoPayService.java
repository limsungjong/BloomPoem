package com.example.bloompoem.service;

import com.example.bloompoem.domain.dto.PickUpDateAndTImeRequest;
import com.example.bloompoem.domain.kakaoPay.PayApprove;
import com.example.bloompoem.domain.kakaoPay.PayOrderProduct;
import com.example.bloompoem.domain.kakaoPay.PayReady;
import com.example.bloompoem.entity.PickUpCartEntity;
import com.example.bloompoem.repository.FlowerRepository;
import com.example.bloompoem.repository.PickUpCartRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;


@Service
@PropertySource("classpath:app.properties")
@RequiredArgsConstructor
public class KakaoPayService {

    @Value("#{environment['kakao.admin']}")
    private String adminKey;

    Logger logger = LoggerFactory.getLogger(KakaoPayService.class);

    private final PickUpCartRepository pickUpCartRepository;

    private final FlowerRepository flowerRepository;

    private final PickUpService pickUpService;

    private final OrderService orderService;

    public PayReady payReady(PickUpDateAndTImeRequest request, String userEmail) {
        PayReady payReady = null;
        RestTemplate template = new RestTemplate();
        String url = "https://kapi.kakao.com/v1/payment/ready";

        Integer totalAmount = 0;
        // 물품 총액을 계산
        for (PayOrderProduct product : request.getOrderList()) {
            totalAmount += (product.getFloristProductPrice() * product.getFlowerCount());
        }
        logger.info("결제 총액 : " + totalAmount);

        List<Integer> cartSeqNumbers = new ArrayList<>();

        request.getOrderList().forEach(flower -> {
            cartSeqNumbers.add(pickUpCartRepository.save(PickUpCartEntity
                    .builder()
                    .flowerNumber(flower.getFlowerNumber())
                    .flowerCount(flower.getFlowerCount())
                    .floristNumber(flower.getFloristNumber())
                    .userEmail(userEmail)
                    .build()).getPickUpCartNumber());
        });

        String itemName = "";
        List<String> itemNames = new ArrayList<>();
        request.getOrderList().forEach(flower -> {
            itemNames.add(flower.getFlowerName());
        });

        for (int i = 0; i < itemNames.size(); i++) {
            itemName = itemName + ',' + itemNames.get(i);
        }

        logger.info(itemName);



        itemName = userEmail + itemName;
        // 카카오가 요구한 결제요청request값을 담아줍니다.
        MultiValueMap<String, String> parameters = new LinkedMultiValueMap<String, String>();
        parameters.add("cid", "TC0ONETIME");
        parameters.add("partner_order_id", itemName);
        parameters.add("partner_user_id", "bloomPoem");
        parameters.add("item_name", itemName);
        parameters.add("quantity", String.valueOf(request.getOrderList().size()));
        parameters.add("total_amount", String.valueOf(totalAmount));
        parameters.add("tax_free_amount", "0");

        parameters.add("approval_url", "http://localhost:9000/pick_up/order/pay/completed"); // 결제승인시 넘어갈 url
        parameters.add("cancel_url", "http://localhost:9000/pick_up/order/pay/cancel"); // 결제취소시 넘어갈 url
        parameters.add("fail_url", "http://localhost:9000/pick_up/order/pay/fail"); // 결제 실패시 넘어갈 url

        logger.info("수량 : " + String.valueOf(request.getOrderList().size()));
        logger.info("파트너ID : " + parameters.get("partner_order_id"));

        HttpEntity<MultiValueMap<String, String>> requestEntity = new HttpEntity<>(parameters, this.getHeaders());

        // 여기서 외부로 연결 됨
        try {
            payReady = template.postForObject(url, requestEntity, PayReady.class);

            if(payReady != null) {
                orderService.detailSaveOrder(request,userEmail,totalAmount);
            } else {
                request.getOrderList().forEach(flower -> {
                    pickUpCartRepository
                            .delete(
                                    pickUpCartRepository
                                            .findByUserEmailAndFlowerNumberAndFloristNumber(
                                                    userEmail
                                                    ,flower.getFlowerNumber()
                                                    ,flower.getFloristNumber()).get());
                });
            }
        } catch (RestClientException e) {
            logger.error("[KakaoPayService] kakao");
        }

        logger.info("결제준비 응답객체" + payReady);
        return payReady;
    }

    public PayApprove payApprove(String tid, String pgToken, String userEmail) {
        String user = userEmail;
        List<PickUpCartEntity> carts = pickUpCartRepository.findByUserEmail(user);
        System.out.println("tid"+tid);

        String itemName = "";
        for (PickUpCartEntity cart : carts) {
            for (int i = 0; i < carts.size(); i++) {
                itemName = itemName + "," + flowerRepository.findById(cart.getFlowerNumber()).get().getFlowerName();
            }
        }
        String order_id = userEmail + itemName;

        // request값 담기.
        MultiValueMap<String, String> parameters = new LinkedMultiValueMap<String, String>();
        parameters.add("cid", "TC0ONETIME");
        parameters.add("tid", tid);
        parameters.add("partner_order_id", order_id); // 주문명
        parameters.add("partner_user_id", "회사명");
        parameters.add("pg_token", pgToken);

        // 하나의 map안에 header와 parameter값을 담아줌
        HttpEntity<MultiValueMap<String, String>> requestEntity = new HttpEntity<>(parameters, this.getHeaders());

        // 외부 url 통신
        RestTemplate template = new RestTemplate();
        String url = "https://kapi.kakao.com/v1/payment/approve";
        // 보낼 외부 url, 요청 메시지(header, parameter), 처리후 값을 받아올 클래스
        PayApprove payApprove = template.postForObject(url, requestEntity, PayApprove.class);

        return payApprove;
    }


    private HttpHeaders getHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", adminKey);
        headers.set("Content-type", "application/x-www-form-urlencoded;charset=utf-8");
        return headers;
    }
}
