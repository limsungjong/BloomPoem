package com.example.bloompoem.service;

import com.example.bloompoem.domain.dto.PickUpDateAndTImeRequest;
import com.example.bloompoem.domain.kakaoPay.PayReady;
import com.example.bloompoem.dto.KakaoApprovar;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;


@Service
@PropertySource("classpath:app.properties")
@RequiredArgsConstructor
public class KakaoPayService {

    @Value("#{environment['kakao.admin']}")
    private String adminKey;

    Logger logger = LoggerFactory.getLogger(KakaoPayService.class);

    private final PickUpService pickUpService;

    private final OrderService orderService;

    public PayReady payReady(
            PickUpDateAndTImeRequest request,
            Integer totalAmount,
            Integer orderSeq,
            String userEmail) {

        PayReady payReady = null;
        RestTemplate template = new RestTemplate();
        String url = "https://kapi.kakao.com/v1/payment/ready";

        logger.info("결제 총액 : " + totalAmount);

        String itemName = "";
        List<String> itemNames = new ArrayList<>();
        request.getOrderList().forEach(flower -> {
            itemNames.add(flower.getFlowerName());
        });

        for (int i = 0; i < itemNames.size(); i++) {
            if (i == 0) {
                itemName = itemNames.get(i);
            } else {
                itemName = ", " + itemNames.get(i);
            }
        }


        // 카카오가 요구한 결제요청request값을 담아줍니다.
        MultiValueMap<String, String> parameters = new LinkedMultiValueMap<String, String>();
        parameters.add("cid", "TC0ONETIME");
        parameters.add("partner_order_id", String.valueOf(orderSeq));
        parameters.add("partner_user_id", userEmail);
        parameters.add("item_name", itemName);
        parameters.add("quantity", String.valueOf(request.getOrderList().size()));
        parameters.add("total_amount", String.valueOf(totalAmount));
        parameters.add("tax_free_amount", "0");

        parameters.add("approval_url", "http://localhost:9000/pick_up/order/pay/completed"); // 결제승인시 넘어갈 url
        parameters.add("cancel_url", "http://localhost:9000/pick_up/order/pay/cancel"); // 결제취소시 넘어갈 url
        parameters.add("fail_url", "http://localhost:9000/pick_up/order/pay/fail"); // 결제 실패시 넘어갈 url

        logger.info("수량 : " + request.getOrderList().size());
        logger.info("파트너ID : " + parameters.get("partner_order_id"));

        HttpEntity<MultiValueMap<String, String>> requestEntity = new HttpEntity<>(parameters, this.getHeaders());

        // restTemplate 사용하여 외부로 연결
        try {
            payReady = template.postForObject(url, requestEntity, PayReady.class);
        } catch (RestClientException e) {
            logger.error("[KakaoPayService] template Error");
        } catch (Exception e) {
            logger.error("[KakaoPayService] any Error");
        }
        logger.info("결제준비 응답객체" + payReady);
        return payReady;
    }

    public KakaoApprovar payApprove(String tid, String pgToken, String orderId, String userEmail) {
        String user = userEmail;
        KakaoApprovar KakaoApprovar = null;

        // request값 담기.
        MultiValueMap<String, String> parameters = new LinkedMultiValueMap<String, String>();
        parameters.add("cid", "TC0ONETIME");
        parameters.add("tid", tid);
        parameters.add("partner_order_id", orderId); // 주문명
        parameters.add("partner_user_id", userEmail);
        parameters.add("pg_token", pgToken);

        // 하나의 map안에 header와 parameter값을 담아줌
        HttpEntity<MultiValueMap<String, String>> requestEntity = new HttpEntity<>(parameters, this.getHeaders());

        // 외부 url 통신
        RestTemplate template = new RestTemplate();
        String url = "https://kapi.kakao.com/v1/payment/approve";
        // 보낼 외부 url, 요청 메시지(header, parameter), 처리후 값을 받아올 클래스

        try {
            KakaoApprovar = template.postForObject(new URI("https://kapi.kakao.com/v1/payment/approve"), requestEntity, KakaoApprovar.class);

            if(KakaoApprovar !=null){
                pickUpService.pickUpCartDeleteByPickOrderSeq(Integer.valueOf(orderId));
                orderService.updateOrderStatus(Integer.parseInt(orderId), 3);
            }
        }catch(RestClientException e)
        {
            logger.error("[KakaoPayService] kakaoPayApprove RestClientException", e);
        }
        catch(URISyntaxException e)
        {
            logger.error("[KakaoPayService] kakaoPayApprove URISyntaxException", e);
        }

        return KakaoApprovar;
    }

    private HttpHeaders getHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", adminKey);
        headers.add("Content-type", MediaType.APPLICATION_FORM_URLENCODED_VALUE + ";charset=utf-8");
        return headers;
    }
}
