package com.example.bloompoem.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;

@CrossOrigin(origins = "http://192.168.45.148:5500/")
@Controller
public class TestController {
    @GetMapping(value = "/test")
    public String testCall(HttpServletRequest request) {
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
}
