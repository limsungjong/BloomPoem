package com.example.bloompoem.controller;

import com.example.bloompoem.entity.ShoppingOrder;
import com.example.bloompoem.entity.UserEntity;
import com.example.bloompoem.service.ProductService;
import com.example.bloompoem.service.UserService;
import com.example.bloompoem.util.JwtUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartRequest;

import java.time.LocalDateTime;
import java.util.Date;

@Controller
public class MyPageController {

    @Value("#{environment['jwt.secret']}")
    private String secretKey;
    @Autowired
    UserService userService;
    @Autowired
    ProductService productService;

    private static Logger logger = LoggerFactory.getLogger(MyPageController.class);

    //범수 시작
    //myPage로 보내는 기능 보내면서 쿠키를 읽어 모델에 user를 담아서 보냄
    @GetMapping("/my_page")
    public String MyPageGo(@CookieValue(value = "Authorization") String cookie, Model model){
        String userEmail =  JwtUtil.getUserName(cookie, secretKey);
        if(userEmail != null) {
            UserEntity user  = userService.tokenToUserEntity(cookie);
            model.addAttribute("user", user);
            return "/myPage";
        }else {
            return "/signin";
        }
    }
    @PostMapping("/my_page/orders_view")
    public ResponseEntity<Page<ShoppingOrder>> ordersView (String userEmail, Date startDate, Date endDate , @PageableDefault(size = 2)Pageable pageable){
        return ResponseEntity.ok(productService.ordersView(endDate, startDate, userEmail, pageable));
    }
    // myPage로 보내는 기능 끝
    // 배송받았다는 버튼 클릭 시 orderNumber update 시작
    @PostMapping("/my_page/order_status_update")
    public  ResponseEntity<String> statusUpdate (int shoppingOrderNumber){
        ShoppingOrder order = productService.orderSelect(shoppingOrderNumber);
        logger.error(""+order);
        order.setShoppingOrderStatus(5);
        productService.orderUpdate(order);

        return  ResponseEntity.ok("success");
    }
    // 배송받았다는 버튼 클릭 시 orderNumber update 시작
    // 리뷰 등록시 리뷰 Insert 시작
//    @PutMapping("/review/write")
//    public ResponseEntity<String> insertReview (MultipartRequest request){
//
//    }


    //범수 끝

    //성종 시작

    //나래 시작
}
