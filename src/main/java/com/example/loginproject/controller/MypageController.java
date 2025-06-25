package com.example.loginproject.controller;

import com.example.loginproject.domain.Member;
import com.example.loginproject.domain.Order;
import com.example.loginproject.service.MemberService;
import com.example.loginproject.service.OrderService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class MypageController {

    private final MemberService memberService;
    private final OrderService orderService;

    @Autowired
    public MypageController(MemberService memberService, OrderService orderService) {
        this.memberService = memberService;
        this.orderService = orderService;
    }

    @GetMapping("/mypage")
    public String mypage(HttpSession session, Model model) {
        String loginUsername = (String) session.getAttribute("loginUser");

        if (loginUsername == null) {
            return "redirect:/login"; // 로그인 안 되어 있으면 로그인 페이지로
        }

        Member member = memberService.findByUsername(loginUsername);
        List<Order> orderList = orderService.getOrdersByMember(member);

        model.addAttribute("member", member);
        model.addAttribute("orders", orderList);

        return "mypage";  // 👉 mypage.html 에서 사용자 정보 + 주문 내역 출력
    }
}
