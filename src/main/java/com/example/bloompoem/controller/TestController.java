package com.example.bloompoem.controller;

import com.example.bloompoem.domain.dto.PickUpCartRequest;
import com.example.bloompoem.entity.PickUpOrderDetailEntity;
import com.example.bloompoem.entity.PickUpOrderEntity;
import com.example.bloompoem.repository.*;
import com.example.bloompoem.service.PickUpService;
import com.example.bloompoem.service.UserService;
import com.example.bloompoem.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "http://192.168.45.148:5500/")
@Controller
@PropertySource("classpath:app.properties")
public class TestController {

    @Autowired
    PickUpOrderRepository pickUpOrderRepository;

    @Autowired
    FlowerRepository flowerRepository;

    @Autowired
    PickUpService pickUpService;

    @Autowired
    FloristProductRepository floristProductRepository;

    @Autowired
    PickUpCartRepository pickUpCartRepository;

    @Autowired
    UserService userService;

    @Autowired
    PickUpOrderDetailRepository pickUpOrderDetailRepository;

    @Value("#{environment['jwt.secret']}")
    private String secretKey;

    @GetMapping(value = "/test")
    public String testCall(HttpServletRequest request, @CookieValue(value = "Authorization") String token) {

        System.out.println(token);
        System.out.println(JwtUtil.getUserName(token, secretKey));
        System.out.println(request.getSession());
        return "test";
    }

    @GetMapping(value = "/test/list")
    @ResponseBody
    public ArrayList<String> testList(Model model) {
        ArrayList<String> arrayList = new ArrayList<String>();
        arrayList.add("cherryBlossom.jpg");
        return arrayList;
    }

    @GetMapping(value = "/test/api")
    public @ResponseBody String test() {
        String orderId = "49";
        pickUpService.pickUpCartDeleteByPickOrderSeq(Integer.valueOf(orderId));
        return "성공";
    }
}
