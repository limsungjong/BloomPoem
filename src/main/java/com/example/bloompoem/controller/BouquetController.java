package com.example.bloompoem.controller;

import com.example.bloompoem.entity.*;
import com.example.bloompoem.repository.PickUpCartRepository;
import com.example.bloompoem.service.BouquetService;
import com.example.bloompoem.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.apache.commons.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileOutputStream;
import java.util.List;
import java.util.UUID;

@Controller
@RequiredArgsConstructor
public class BouquetController {
    Logger logger = LoggerFactory.getLogger(PickUpController.class);
    @Value("#{environment['custom.file']}")
    private String FILE_PATH;
    @Value("#{environment['jwt.secret']}")
    private String secretKey;
    private final BouquetService bouquetService;
    private final PickUpCartRepository pickUpCartRepository;

    @GetMapping("/color/read")
    public ResponseEntity<List<BouquetColor>> colorListView(){

        return ResponseEntity.ok(bouquetService.colorAllView());
    }

    @PostMapping("/bouquet/save")
    public ResponseEntity<Integer> bouquetSave(int totalCost, String bouquetColorRgb, int floristNumber, String cookie ){
        BouquetEntity bouquet = new BouquetEntity();
        FloristEntity florist =new FloristEntity();
        UserEntity user =new UserEntity();
        BouquetColor bouquetColor = new BouquetColor();
        String userEmail = JwtUtil.getUserName(cookie,secretKey);
        logger.error(""+userEmail);

        user.setUserEmail(userEmail);
        florist.setFloristNumber(floristNumber);
        bouquetColor.setBouquetColorRgb(bouquetColorRgb);

        bouquet.setBouquetPrice(totalCost);
        bouquet.setBouquetColor(bouquetColor);
        bouquet.setFlorist(florist);
        bouquet.setUser(user);
        int bouquetNumber =  bouquetService.saveBouqet(bouquet);

        return ResponseEntity.ok(bouquetNumber);
    }

    @PostMapping("/pickup/save_photo")
    public ResponseEntity<?> saveBouquetPhoto (HttpServletRequest req, HttpServletResponse resp , int bouquetNumber){
        String image = req.getParameter("imageURL");

        FileOutputStream stream = null;
        String imageName = UUID.randomUUID().toString().replace("-", "") + ".png";

        try {
            image = image.replaceAll("data:png/jpg;base64,", "");
            byte[] file = Base64.decodeBase64(image);
            File imageFile = new File(FILE_PATH, imageName);
            stream = new FileOutputStream(FILE_PATH+"/"+imageName);
            stream.write(file);
            stream.close();

        } catch (Exception e) {
            logger.error("[savePhoto] Error : " + e);
        }
        BouquetEntity bouquet = bouquetService.selelctBouquet(bouquetNumber);
        bouquet.setBouquetMainImage(imageName);
        bouquetService.saveBouqet(bouquet);
        return ResponseEntity.ok().body("이미지 저장");
    }
    @PostMapping("/pickup/cart/insert")
    public ResponseEntity<?> insertPickUpCart (int floristNumber, int bouquetNumber, String cookie){
        PickUpCartEntity pickUpCart = new PickUpCartEntity();
        BouquetEntity bouquet = new BouquetEntity();

        bouquet.setBouquetNumber(bouquetNumber);

        String userEmail = JwtUtil.getUserName(cookie,secretKey);

        pickUpCart.setBouquet(bouquet);
        pickUpCart.setFlowerCount(1);
        pickUpCart.setUserEmail(userEmail);
        pickUpCart.setFloristNumber(floristNumber);
        pickUpCart.setFlowerNumber(99999);

        pickUpCartRepository.save(pickUpCart);
        return ResponseEntity.ok("성공");
    }
}