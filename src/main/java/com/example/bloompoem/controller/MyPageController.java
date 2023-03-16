package com.example.bloompoem.controller;

import com.example.bloompoem.entity.ProductEntity;
import com.example.bloompoem.entity.ShoppingOrder;
import com.example.bloompoem.entity.ShoppingReview;
import com.example.bloompoem.entity.UserEntity;
import com.example.bloompoem.service.ProductService;
import com.example.bloompoem.service.ShoppingReviewService;
import com.example.bloompoem.service.UserService;
import com.example.bloompoem.util.JwtUtil;
import org.apache.http.util.Args;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.SpringApplication;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.MultipartRequest;

import java.io.File;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.UUID;

@Controller
public class MyPageController {

    @Value("#{environment['jwt.secret']}")
    private String secretKey;
    @Autowired
    private UserService userService;
    @Autowired
    private ProductService productService;
    @Autowired
    private ShoppingReviewService shoppingReviewService;
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
    @PutMapping("/review/write")
    public ResponseEntity<Integer> insertReview (String shoppingReviewContent ,int shoppingReviewScore, int shoppingOrderNumber , int productNumber, String userEmail){
        ShoppingReview review = new ShoppingReview();
        ShoppingOrder shoppingOrder =new ShoppingOrder();
        ProductEntity product = new ProductEntity();
        UserEntity user = new UserEntity();
        review.setShoppingReviewContent(shoppingReviewContent);
        review.setShoppingReviewScore(shoppingReviewScore);
        review.setShoppingReviewRegDate(new Date());
        shoppingOrder.setShoppingOrderNumber(shoppingOrderNumber);
        product.setProductNumber(productNumber);
        user.setUserEmail(userEmail);
        review.setProduct(product);
        review.setShoppingOrder(shoppingOrder);
        review.setUser(user);
        int count = shoppingReviewService.insertReview(review);

        return ResponseEntity.ok(count);
    }
    // 리뷰 등록시 리뷰 Insert 끝
    @PostMapping("/review/save_photo")
    public ResponseEntity<String> insertPhoto(MultipartFile reviewImage ,Integer shoppingReviewNumber){
        logger.error(""+reviewImage);
        String  path =  "C:/project/BloomPoem/src/main/resources/static/image/upload" ;
        //이미지 파일 생성 후 이름 저장 시키기
        String imageName = UUID.randomUUID().toString().replace("-", "")+".jpg";
        File imageFile = new File(path, imageName);
        try {
            reviewImage.transferTo(imageFile);
        }catch (Exception e){
            logger.error("[insertReview] Error : "+e);
        }
        ShoppingReview shoppingReview = shoppingReviewService.reviewSelect(shoppingReviewNumber);
        shoppingReview.setShoppingReviewImage(imageName);
        shoppingReviewService.insertReview(shoppingReview);

        return ResponseEntity.ok("success");
    }
    @PostMapping("/review/exsist_review")
    public ResponseEntity<Boolean> existsReview (int shoppingOrderNumber, int productNumber ){
        return ResponseEntity.ok(shoppingReviewService.existsReview(shoppingOrderNumber, productNumber));
    }
    @PostMapping("/review/read")
    public ResponseEntity<Page<ShoppingReview>> reviewRead (String userEmail , @PageableDefault(size = 6)Pageable pageable){
        return ResponseEntity.ok(shoppingReviewService.readReview(userEmail, pageable));
    }
    @PostMapping("/review/update")
    public ResponseEntity<String> reviewUpdate (int shoppingReviewNumber, String shoppingReviewContent, int shoppingReviewScore){
        ShoppingReview  shoppingReview=shoppingReviewService.reviewSelect(shoppingReviewNumber);
        shoppingReview.setShoppingReviewContent(shoppingReviewContent);
        shoppingReview.setShoppingReviewScore(shoppingReviewScore);
        shoppingReviewService.insertReview(shoppingReview);
        return ResponseEntity.ok("success");
    }
    @DeleteMapping("/review/delete")
    public ResponseEntity<String> reviewDelete(int shoppingReviewNumber){
        shoppingReviewService.deletReview(shoppingReviewNumber);
        return ResponseEntity.ok("success");
    }
    //범수 끝

    //성종 시작

    //나래 시작
}
