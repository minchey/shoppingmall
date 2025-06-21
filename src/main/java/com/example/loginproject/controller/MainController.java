package com.example.loginproject.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import jakarta.servlet.http.HttpSession;
import org.springframework.ui.Model;

@Controller
public class MainController {

    @GetMapping("/main")
    public String mainPage(HttpSession session, Model model) {
        String loginUser = (String) session.getAttribute("loginUser");
        model.addAttribute("loginUser", loginUser);
        return "main";
    }
}
