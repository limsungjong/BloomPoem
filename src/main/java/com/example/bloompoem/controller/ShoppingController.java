package com.example.bloompoem.controller;

import com.example.bloompoem.dto.KakaoApprovar;
import com.example.bloompoem.dto.KakaoOrder;
import com.example.bloompoem.dto.KakaoReady;
import com.example.bloompoem.entity.*;
import com.example.bloompoem.service.ProductService;

import com.example.bloompoem.service.UserService;
import com.example.bloompoem.util.JwtUtil;

import net.minidev.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


import java.io.Console;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


@Controller
@PropertySource("classpath:app.properties")
public class ShoppingController {
    @Autowired
    private ProductService productService;
    @Autowired
    private UserService userService;

    @Value("#{environment['jwt.secret']}")
    private String secretKey;


    private static Logger logger = LoggerFactory.getLogger(ShoppingController.class);

    @GetMapping("/shopping")
    public String shopping (){

        return "/shop/shopMain";
    }

    @GetMapping("/shopping/category")
    public String shoppingCategory (Model model, int category,String searchValue){
        model.addAttribute("category", category);
        model.addAttribute("page" , 1);
        model.addAttribute("searchValue", searchValue);
        if(category > 9 && category<0){
            return  "/shopping";
        }
        return "/shop/shopCategory";
    }

    //카테고리별 물건 불러오기
    @GetMapping("/shopping/productListView")
    public ResponseEntity<Page<ProductEntity>> view (int category , @PageableDefault(size = 6) Pageable pageable) {

        if(category != '\u0000'){
            Page<ProductEntity> product = productService.categoryProductView(category, pageable);
            if(product != null){
                return ResponseEntity.ok(product);
            }else {
                return ResponseEntity.status(HttpStatus.BAD_GATEWAY).body(null);
            }

        }else{
            return ResponseEntity.status(HttpStatus.BAD_GATEWAY).body(null);
        }
    }
    @GetMapping("/shopping/product_detail")
    public String productDetail (Model model,int category, int productNumber) {
        ProductEntity product =null;
        if (category != 0 && productNumber != 0) {
            product = productService.viewOne(productNumber);
            model.addAttribute("product", product);
            model.addAttribute("productNumber", productNumber);
            model.addAttribute("category", category);
            return "/shop/shopProductDetail";
        } else {
            return "/shop/shopMain";
        }
    }
    @GetMapping("/shopping/search")
    public ResponseEntity<Page<ProductEntity>> searchProduct (String searchValue, @PageableDefault(size = 6)Pageable pageable){

        Page<ProductEntity> product = productService.searchProduct(searchValue, pageable);
        return ResponseEntity.ok(product);
    }
    @GetMapping("/shopping/product_info")
    public  ResponseEntity<ProductEntity> productInfo(int productNumber){
        ProductEntity product = productService.viewOne(productNumber);
        return ResponseEntity.ok(product);
    }

    @PutMapping("/shopping/cartSave")
    public ResponseEntity<String> cartSave (int productNumber , int count, String cookie){
        ShoppingCartEntity cart = new ShoppingCartEntity();
        ProductEntity product = new ProductEntity();
        cart.setShoppingCartCount(count);
        cart.setUserEmail(JwtUtil.getUserName(cookie, secretKey));
        product.setProductNumber(productNumber);
        cart.setProduct(product);
        productService.saveCart(cart);
        return ResponseEntity.ok("success");
    }
    @PostMapping("/shopping/cart_exists_product")
    public ResponseEntity<String> existsProduct (int productNumber, String cookie){
        String userEmail = JwtUtil.getUserName(cookie, secretKey);
        if(!productService.existsProduct(productNumber, userEmail)){
            return ResponseEntity.ok("success");
        }else{
            return ResponseEntity.status(HttpStatus.IM_USED).body("alreadyProduct");
        }
    }
    @PostMapping("/shopping/cartCount")
    public ResponseEntity<Integer> cartCount ( String cookie){
        String userEmail = JwtUtil.getUserName(cookie, secretKey);
        int count = productService.countCart(userEmail);

        return ResponseEntity.ok(count);
    }
    @GetMapping("/shopping/cart")
    public String cartView (){
        return "/shop/shopCart";
    }
    @PostMapping("/shopping/viewCart")
    public ResponseEntity<List<ShoppingCartEntity>> viewCart (String cookie){
        String userEmail =  JwtUtil.getUserName(cookie, secretKey);
        List<ShoppingCartEntity> cart = productService.viewCart(userEmail);
        return ResponseEntity.ok(cart);
    }
    @DeleteMapping("/shopping/cart/delete")
    public ResponseEntity<String > deleteCart (String cookie , int shoppingCartNumber){
        String userEmail = JwtUtil.getUserName(cookie, secretKey);
        productService.deleteCart(shoppingCartNumber, userEmail);
        return ResponseEntity.ok("success");
    }

    @PutMapping("/shopping/cart/update_count")
    public ResponseEntity<String> updateCount(String cookie, int shoppingCartNumber , int count){
        String userEmail = JwtUtil.getUserName(cookie, secretKey);
        ShoppingCartEntity cart = productService.oneCartSelect(shoppingCartNumber);
        cart.setShoppingCartCount(count);


        productService.saveCart(cart);
        return ResponseEntity.ok("success");
    }
    @PostMapping("/shopping/payment/oneProduct")
    public String oneProductPayment(Model model,int shoppingCartNumber,  String cookie){
        UserEntity user = userService.tokenToUserEntity(cookie);
        model.addAttribute("user", user);
        model.addAttribute("shoppingCartNumber", shoppingCartNumber);
        return "/shop/shopPayment";
    }

    @PostMapping("/shoping/payment/find_cartNumber")
    public ResponseEntity<Integer> findCartNumber(int productNumber, String cookie){
        String userEmail = JwtUtil.getUserName(cookie, secretKey);
        int count = productService.cartNumberSelecter(productNumber, userEmail);
        if(count > 0){
           return ResponseEntity.ok(count);
        }else {
           return ResponseEntity.status(HttpStatus.NO_CONTENT).body(0);
        }
    }
    @PostMapping("/shopping/payment/oneview")
    public ResponseEntity<ShoppingCartEntity> oneView (int shoppingCartNumber, String cookie){
        String userEmail =JwtUtil.getUserName(cookie,secretKey);
        ShoppingCartEntity cart = productService.oneCartSelect(shoppingCartNumber);
        return ResponseEntity.ok(cart);
    }


    @PutMapping("/shopping/payment/insert_order")
    public ResponseEntity<JSONObject> insertOrder(String cookie, int shoppingTotalPrice, String itemName){
        String userEmail =JwtUtil.getUserName(cookie,secretKey);
        String orderNumber = productService.orderInsert(userEmail, shoppingTotalPrice);

        KakaoOrder kakaoOrder =new KakaoOrder();
        kakaoOrder.setPartnerOrderId(orderNumber);
        kakaoOrder.setPartnerUserId(userEmail);
        kakaoOrder.setItemName(itemName);
        kakaoOrder.setQuantity(1);
        kakaoOrder.setTotalAmount(shoppingTotalPrice);
        kakaoOrder.setTaxFreeAmount(0);

        KakaoReady kakaoReady = productService.kakaoReady(kakaoOrder);
        logger.error(""+kakaoReady);

        JSONObject json = new JSONObject();
        json.put("userEmail", userEmail);
        json.put("orderId", orderNumber);
        json.put("tId", kakaoReady.getTid());
        json.put("pcUrl", kakaoReady.getNext_redirect_pc_url());


        return ResponseEntity.ok(json);
    }
    @PostMapping("/shopping/kakao/payPopUp")
    public String payPopup (Model model , String pcUrl, String orderId , String tId, String userEmail){
        model.addAttribute("pcUrl", pcUrl);
        model.addAttribute("orderId", orderId);
        model.addAttribute("tId", tId);
        model.addAttribute("userEmail", userEmail);

        return "/shop/popup";
    }
    @PostMapping("/shopping/kakao/payResult")
    public String payResult (Model model, String orderId, String tId, String pgToken, String cookie){
        String userEmail =JwtUtil.getUserName(cookie,secretKey);
        KakaoApprovar kakaoApprovar = null;
        KakaoOrder kakakOrder = new KakaoOrder();
        kakakOrder.setPartnerUserId(userEmail);
        kakakOrder.setPartnerOrderId(orderId);
        kakakOrder.setTId(tId);
        kakakOrder.setPgToken(pgToken);
        kakaoApprovar = productService.kakaoPayApprove(kakakOrder);
        if(kakaoApprovar != null) {
            ShoppingOrder order = new ShoppingOrder();
            model.addAttribute("kakaoApprovar", kakaoApprovar);
            order.setShoppingOrderNumber(Integer.parseInt(orderId));
            order.setShoppingOrderDate(LocalDate.ofInstant(kakaoApprovar.getApproved_at().toInstant(), ZoneId.systemDefault()));
            order.setShoppingOrderStatus(3);
            order.setShoppingTotalPrice(kakaoApprovar.getAmount().getTotal());
            order.setShoppingRealPrice(kakaoApprovar.getAmount().getTotal());
            order.setUserEmail(userEmail);
            productService.orderUpdate(order);
            return "/shop/shopPaymentSuccess";
        }
        else {
            productService.orderDelete(Integer.parseInt(orderId));
            return "/shop/paymentFail";
        }
    }
    @GetMapping("/shopping/kakao/payResult")
    public String payResult(Model model, String pg_token){
        model.addAttribute("pgToken", pg_token);

        return "/shop/shopPayResult";
    }
    @PostMapping("/shopping/payment/success")
    public String  paymentSuccess (Model model, int orderId){
        model.addAttribute("orderId", orderId);
        return "/shop/paymentApproval";
    }

    @PostMapping("/shopping/success/cartDelete")
    public ResponseEntity<String> cartSelectAndDelete (String productName, String cookie ,int shoppingOrderId){
        String userEmail =JwtUtil.getUserName(cookie,secretKey);
        ShoppingCartEntity cart = productService.productNameCartSelecter(productName,userEmail);
        ShoppingOrderDetail orderDetail = new ShoppingOrderDetail();
        ProductEntity product =new ProductEntity();
        ShoppingOrder order = new ShoppingOrder();
        product.setProductNumber(cart.getProduct().getProductNumber());
        order.setShoppingOrderNumber(shoppingOrderId);
        orderDetail.setProduct(product);

        orderDetail.setShoppingOrderDetailCount(cart.getShoppingCartCount());
        logger.error(""+ orderDetail.getShoppingOrderDetailCount());
        orderDetail.setUserEmail(userEmail);
        logger.error("" + orderDetail.getUserEmail());
        orderDetail.setShoppingOrder(order);



        productService.orderDetailInsert(orderDetail);
        productService.cartDelete(cart.getShoppingCartNumber());


        return ResponseEntity.ok("success");
    }

    @PostMapping("/shopping/success/orderDetailView")
    public ResponseEntity<List<ShoppingOrderDetail>> orderDetailView (int orderNumber , String cookie){
        String userEmail =JwtUtil.getUserName(cookie,secretKey);

        return ResponseEntity.ok(productService.orderDetails(userEmail,orderNumber));
    }


}
