package com.example.membershipmanagementsystem.member;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class loginWebController {
    @GetMapping("/loginPage")
    public String loginPage(){
        return "loginPage";
    }
}