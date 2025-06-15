package com.example.loginproject.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import jakarta.servlet.http.HttpSession;

@Controller
public class MainController {

    @GetMapping("/main")
    public String mainPage(HttpSession session) {
        /*if (session.getAttribute("loginUser") == null) {
         //   return "redirect:/login"; // 로그인 안 했으면 다시 로그인 페이지로
        }*/
        return "main"; // src/main/resources/templates/main.html 로 이동
    }
}
