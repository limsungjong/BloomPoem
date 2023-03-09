package com.example.bloompoem.service;


import com.example.bloompoem.dto.KakaoApprovar;
import com.example.bloompoem.dto.KakaoOrder;
import com.example.bloompoem.dto.KakaoReady;
import com.example.bloompoem.entity.ProductEntity;
import com.example.bloompoem.entity.ShoppingCartEntity;
import com.example.bloompoem.entity.ShoppingOrder;
import com.example.bloompoem.repository.ProductRepository;
import com.example.bloompoem.repository.ShoppingCartRepository;
import com.example.bloompoem.repository.ShoppingOrderRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;


import java.net.URI;
import java.net.URISyntaxException;
import java.util.Date;
import java.util.List;

@Service
public class ProductService {
    @Autowired
    private ProductRepository productDao;
    @Autowired
    private ShoppingCartRepository cartDao;
    @Autowired
    private ShoppingOrderRepository shoppingOrderDao;
    private static Logger logger = LoggerFactory.getLogger(ProductService.class);

    public Page<ProductEntity> categoryProductView (int category ,Pageable pageable){
        Page<ProductEntity> product = null;

        try{
            product = productDao.findAllByProductCategory(category, pageable);
        }catch(Exception e){
            logger.error("[ProductService] categoryProductView Exception ", e);
        }
        return product;
    }
    public Page<ProductEntity> productView(Pageable pageable){
        Page<ProductEntity> product=null;
        try{
            product = productDao.findAll(pageable);
        }catch(Exception e){
            logger.error("[ProductService] ProductView Exception ", e);
        }
        return product;
    }
    public ProductEntity viewOne (int productNumber){
        ProductEntity product = productDao.findByProductNumber(productNumber);
        return product;
    }
    public Page<ProductEntity> searchProduct(String searchValue, Pageable pageable){
        Page<ProductEntity> product = productDao.findByProductNameContaining(searchValue, pageable);
        if(product == null){
            product= null;
        }
        return product;
    }
    public void saveCart(ShoppingCartEntity cart){

        cartDao.saveAndFlush(cart);
    }
    public boolean existsProduct(int productNumber, String userEmail){
        ProductEntity product =new ProductEntity();
        product.setProductNumber(productNumber);
        return cartDao.existsByProductAndUserEmail(product, userEmail);
    }
    public int countCart(String userEmail){
        return cartDao.countByUserEmail(userEmail);
    }
    public List<ShoppingCartEntity> viewCart(String userEmail){
        return cartDao.findByUserEmailOrderByShoppingCartNumberDesc(userEmail);
    }
    public int deleteCart(int shoppingCartNumber, String userEmail){
        return cartDao.deleteByShoppingCartNumberAndUserEmail(shoppingCartNumber, userEmail);
    }
    public ShoppingCartEntity oneCartSelect (int shoppingCartNumber){
        return cartDao.findByShoppingCartNumber(shoppingCartNumber);
    }
    public int cartNumberSelecter(int productNumber, String userEmail){
        ProductEntity product = new ProductEntity();
        product.setProductNumber(productNumber);
        return cartDao.findByProductAndUserEmail(product, userEmail).getShoppingCartNumber();
    }
    public String orderInsert(String userEmail, int shoppingTotalPrice){
        ShoppingOrder order = new ShoppingOrder();
        order.setShoppingOrderDate(new Date());
        order.setShoppingOrderStatus(1);
        order.setUserEmail(userEmail);
        order.setShoppingTotalPrice(shoppingTotalPrice);
        order.setShoppingRealPrice(shoppingTotalPrice);
        shoppingOrderDao.save(order);
        return ""+order.getShoppingOrderNumber();
    }
    public KakaoReady kakaoReady(KakaoOrder kakaoOrder){
        KakaoReady kakaoReady =null;
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.setRequestFactory(new HttpComponentsClientHttpRequestFactory());
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization","KakaoAK 8e4474a2e8cfcea2248551939170e2b6");
        headers.add("Content-type", MediaType.APPLICATION_FORM_URLENCODED_VALUE + ";charset=utf-8");

        MultiValueMap<String, String> params = new LinkedMultiValueMap<String, String>();
        params.add("cid","TC0ONETIME");
        params.add("partner_order_id", String.valueOf(kakaoOrder.getPartnerOrderId()));
        params.add("partner_user_id", kakaoOrder.getPartnerUserId());
        params.add("item_name", kakaoOrder.getItemName());
        params.add("quantity", String.valueOf(kakaoOrder.getQuantity()));
        params.add("total_amount", String.valueOf(kakaoOrder.getTotalAmount()));
        params.add("tax_free_amount", String.valueOf(kakaoOrder.getTaxFreeAmount()));

        params.add("approval_url","http://localhost:9000/shopping/kakao/payResult" );
        params.add("cancel_url", "http://localhost:9000/shopping/kakao/payResult" );
        params.add("fail_url","http://localhost:9000/shopping/kakao/payResult" );

        HttpEntity<MultiValueMap<String, String>> body = new HttpEntity<MultiValueMap<String,String>>(params, headers);


        try {
            kakaoReady = restTemplate.postForObject(new URI("https://kapi.kakao.com/v1/payment/ready"), body, KakaoReady.class);
            if(kakaoReady !=null){
                kakaoOrder.setTId(kakaoReady.getTid());
            }
        }catch(RestClientException e)
        {
            logger.error("[KakaoPayService] kakaoPayApprove RestClientException", e);
        }
        catch(URISyntaxException e)
        {
            logger.error("[KakaoPayService] kakaoPayApprove URISyntaxException", e);
        }

        return kakaoReady;
    }

    public KakaoApprovar kakaoPayApprove (KakaoOrder kakaoOrder){
        KakaoApprovar kakaoApprovar = null;

        RestTemplate restTemplate =new RestTemplate();
        restTemplate.setRequestFactory(new HttpComponentsClientHttpRequestFactory());
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization","KakaoAK 8e4474a2e8cfcea2248551939170e2b6");
        headers.add("Content-type", MediaType.APPLICATION_FORM_URLENCODED_VALUE + ";charset=utf-8");

        MultiValueMap<String, String> params = new LinkedMultiValueMap<String, String>();
        params.add("cid","TC0ONETIME");
        params.add("tid", kakaoOrder.getTId());
        params.add("partner_order_id", kakaoOrder.getPartnerOrderId());
        params.add("partner_user_id", kakaoOrder.getPartnerUserId());
        params.add("pg_token", kakaoOrder.getPgToken());

        HttpEntity<MultiValueMap<String, String>> body = new HttpEntity<MultiValueMap<String,String>>(params, headers);
        try {
            kakaoApprovar = restTemplate.postForObject(new URI("https://kapi.kakao.com/v1/payment/approve"), body, KakaoApprovar.class);
            if(kakaoApprovar !=null){
                kakaoApprovar.setTid(kakaoApprovar.getTid());
            }
        }catch(RestClientException e)
        {
            logger.error("[KakaoPayService] kakaoPayApprove RestClientException", e);
        }
        catch(URISyntaxException e)
        {
            logger.error("[KakaoPayService] kakaoPayApprove URISyntaxException", e);
        }
        return kakaoApprovar;
    }

}
