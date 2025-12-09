package com.example.membershipmanagementsystem.member;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class loginCredentials {
    @PostMapping("/authenticate")
    public String loginPage(@RequestParam String username, @RequestParam String password, Model model){
        if ("admin".equals(username) && "admin".equals(password)){
            return "redirect:/dashboard";
        } else {
            model.addAttribute("error", "Incorrect username or password. Please double check your username or password");
            return "loginPage";
        }

    }
}