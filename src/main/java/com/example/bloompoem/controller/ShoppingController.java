package com.example.bloompoem.controller;

import com.example.bloompoem.entity.ProductEntity;
import com.example.bloompoem.entity.ShoppingCartEntity;
import com.example.bloompoem.security.JwtFilter;
import com.example.bloompoem.service.ProductService;

import com.example.bloompoem.service.UserService;
import com.example.bloompoem.util.JwtUtil;
import lombok.RequiredArgsConstructor;
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
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;


import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
@PropertySource("classpath:app.properties")
public class ShoppingController {
    @Autowired
    private ProductService productService;


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
        cart.setProductNumber(productNumber);
        cart.setShoppingCartCount(count);
        cart.setUserEmail(JwtUtil.getUserName(cookie, secretKey));
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

}
