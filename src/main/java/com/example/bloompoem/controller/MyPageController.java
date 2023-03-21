package com.example.bloompoem.controller;

import com.example.bloompoem.domain.dto.ResponseCode;
import com.example.bloompoem.entity.*;
import com.example.bloompoem.exception.CustomException;
import com.example.bloompoem.repository.FloristReviewRepository;
import com.example.bloompoem.repository.PickUpOrderDetailRepository;
import com.example.bloompoem.repository.PickUpOrderRepository;
import com.example.bloompoem.service.*;
import com.example.bloompoem.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import net.minidev.json.annotate.JsonIgnore;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.Date;
import java.util.UUID;

@Controller
@RequiredArgsConstructor
@PropertySource("classpath:app.properties")
public class MyPageController {

    @Value("#{environment['jwt.secret']}")
    private String secretKey;
    private final UserService userService;
    private final ProductService productService;
    private final ShoppingReviewService shoppingReviewService;
    private final PickUpOrderDetailRepository pickUpOrderDetailRepository;
    private final PickUpOrderRepository pickUpOrderRepository;
    private final OrderService orderService;
    private final PickUpOrderReviewService pickUpOrderReviewService;
    private final FloristReviewRepository floristReviewRepository;
    private static final Logger logger = LoggerFactory.getLogger(MyPageController.class);

    @Value("#{environment['file.path']}")
    private String FILE_PATH;

    //범수 시작
    //myPage로 보내는 기능 보내면서 쿠키를 읽어 모델에 user를 담아서 보냄
    @GetMapping("/my_page")
    public String MyPageGo(@CookieValue(value = "Authorization") String cookie, Model model) {
        String userEmail = JwtUtil.getUserName(cookie, secretKey);
        if (userEmail != null) {
            UserEntity user = userService.tokenToUserEntity(cookie);
            model.addAttribute("user", user);
            return "/myPage";
        } else {
            return "/signin";
        }
    }

    @PostMapping("/my_page/orders_view")
    public ResponseEntity<Page<ShoppingOrder>> ordersView(String userEmail, Date startDate, Date endDate, @PageableDefault(size = 2) Pageable pageable) {
        return ResponseEntity.ok(productService.ordersView(endDate, startDate, userEmail, pageable));
    }

    // myPage로 보내는 기능 끝
    // 배송받았다는 버튼 클릭 시 orderNumber update 시작
    @PostMapping("/my_page/order_status_update")
    public ResponseEntity<String> statusUpdate(int shoppingOrderNumber) {
        ShoppingOrder order = productService.orderSelect(shoppingOrderNumber);
        logger.error("" + order);
        order.setShoppingOrderStatus(5);
        productService.orderUpdate(order);

        return ResponseEntity.ok("success");
    }

    // 배송받았다는 버튼 클릭 시 orderNumber update 시작
    // 리뷰 등록시 리뷰 Insert 시작
    @PutMapping("/review/write")
    public ResponseEntity<Integer> insertReview(String shoppingReviewContent, int shoppingReviewScore, int shoppingOrderNumber, int productNumber, String userEmail) {
        ShoppingReview review = new ShoppingReview();
        ShoppingOrder shoppingOrder = new ShoppingOrder();
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
    public ResponseEntity<String> insertPhoto(MultipartFile reviewImage, Integer shoppingReviewNumber) {
        logger.error("" + reviewImage);
        //이미지 파일 생성 후 이름 저장 시키기
        String imageName = UUID.randomUUID().toString().replace("-", "") + ".jpg";
        File imageFile = new File(FILE_PATH, imageName);
        try {
            reviewImage.transferTo(imageFile);
        } catch (Exception e) {
            logger.error("[insertReview] Error : " + e);
        }
        ShoppingReview shoppingReview = shoppingReviewService.reviewSelect(shoppingReviewNumber);
        shoppingReview.setShoppingReviewImage(imageName);
        shoppingReviewService.insertReview(shoppingReview);

        return ResponseEntity.ok("success");
    }

    @PostMapping("/review/exsist_review")
    public ResponseEntity<Boolean> existsReview(int shoppingOrderNumber, int productNumber) {
        return ResponseEntity.ok(shoppingReviewService.existsReview(shoppingOrderNumber, productNumber));
    }

    @PostMapping("/review/read")
    public ResponseEntity<Page<ShoppingReview>> reviewRead(String userEmail, @PageableDefault(size = 6) Pageable pageable) {
        return ResponseEntity.ok(shoppingReviewService.readReview(userEmail, pageable));
    }

    @PostMapping("/review/update")
    public ResponseEntity<String> reviewUpdate(int shoppingReviewNumber, String shoppingReviewContent, int shoppingReviewScore) {
        ShoppingReview shoppingReview = shoppingReviewService.reviewSelect(shoppingReviewNumber);
        shoppingReview.setShoppingReviewContent(shoppingReviewContent);
        shoppingReview.setShoppingReviewScore(shoppingReviewScore);
        shoppingReviewService.insertReview(shoppingReview);
        return ResponseEntity.ok("success");
    }

    @DeleteMapping("/review/delete")
    public ResponseEntity<String> reviewDelete(int shoppingReviewNumber) {
        shoppingReviewService.deletReview(shoppingReviewNumber);
        return ResponseEntity.ok("success");
    }
    //범수 끝

    //성종 시작
    @PostMapping("/myPage/get/reviewList")
    @ResponseBody
    public ResponseEntity<Page<PickUpOrderEntity>> getOrderList(String userEmail, Date startDate, Date endDate, @PageableDefault(size = 2) Pageable pageable
    ) {
        return ResponseEntity.ok().body(orderService.pickUpOrderView(endDate, startDate, userEmail, pageable));
    }

    @PostMapping("/myPage/pick_up/order_status_update")
    public ResponseEntity<String> pickUpOrderStatusUpdate(int pickUpOrderNumber) {
        if (pickUpOrderRepository.findById(pickUpOrderNumber).isPresent()) {
            logger.info(pickUpOrderNumber + "");
            orderService.updateOrderStatus(pickUpOrderNumber, 5);
            return ResponseEntity.ok("success");
        }
        throw new CustomException(ResponseCode.NOT_FOUND_ORDER);
    }

    @PostMapping("/review/check_review")
    public ResponseEntity<Boolean> checkReview(int pickUpOrderNumber) {
        return ResponseEntity.ok(pickUpOrderReviewService.checkPickUpOrderReview(pickUpOrderNumber));
    }

    @PostMapping("/review/pick_up/write")
    public ResponseEntity<Integer> insertPickUpReview(
            int floristNumber,
            String userEmail,
            int pickUpOrderNumber,
            String pickUpOrderContent,
            char pickUpOrderScore) {

        logger.info("floristNumber : " + floristNumber);
        logger.info("userEmail : " + userEmail);
        logger.info("pickUpOrderNumber : " + pickUpOrderNumber);
        logger.info("pickUpOrderContent : " + pickUpOrderContent);
        logger.info("pickUpOrderScore : " + pickUpOrderScore);
        FloristReviewEntity reviewEntity = pickUpOrderReviewService.saveOrderReview(
                floristNumber,
                userEmail,
                pickUpOrderNumber,
                pickUpOrderContent,
                pickUpOrderScore
        );

        return ResponseEntity.ok().body(reviewEntity.getFloristReviewNumber());
    }

    @PostMapping(value = "/review/pick_up/save_photo")
    public ResponseEntity<String> pickUpReviewSavePhoto(
            MultipartFile reviewImage, Integer reviewSeq) {

        logger.error("reviewImage : " + reviewImage);
        logger.info("reviewSeq : " + reviewSeq);
        //이미지 파일 생성 후 이름 저장 시키기
        String imageName = UUID.randomUUID().toString().replace("-", "") + ".jpg";
        File imageFile = new File(FILE_PATH, imageName);
        try {
            reviewImage.transferTo(imageFile);
        } catch (Exception e) {
            logger.error("[insertReview] Error : " + e);
        }
        pickUpOrderReviewService.saveOrderReviewImage(reviewSeq, imageName);

        return ResponseEntity.ok().body("이미지 저장");
    }

    @PostMapping("/pick_up/review/read")
    @ResponseBody
    @JsonIgnore
    public ResponseEntity<Page<FloristReviewEntity>> pickUpReviewRead(String userEmail, @PageableDefault(size = 6) Pageable pageable) {
        return ResponseEntity.ok(pickUpOrderReviewService.floristReviewView(userEmail, pageable));
    }

    @PostMapping("/pick_up/review/update")
    public ResponseEntity<?> pickUpReviewUpdate(
            int orderReviewNumber,
            String pickUpOrderContent,
            char pickUpOrderScore
    ) {
        if(floristReviewRepository.existsById(orderReviewNumber)) {
            pickUpOrderReviewService.floristReviewUpdate(orderReviewNumber,pickUpOrderContent,pickUpOrderScore);
        } else throw new CustomException(ResponseCode.INVALID_REQUEST);
        return ResponseEntity.ok().body("성공");
    }

    @DeleteMapping("/pick_up/review/delete")
    public ResponseEntity<?> pickUpReviewDelete(int orderReviewNumber) {
        if(floristReviewRepository.existsById(orderReviewNumber)) {
            pickUpOrderReviewService.floristReviewDelete(orderReviewNumber);
        } else throw new CustomException(ResponseCode.INVALID_REQUEST);
        return ResponseEntity.ok().body("success");
    }
    //나래 시작
}
