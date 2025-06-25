package com.example.loginproject.controller;

import com.example.loginproject.domain.Member;
import com.example.loginproject.dto.CartItem;
import com.example.loginproject.service.MemberService;
import com.example.loginproject.service.OrderService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/order")
public class OrderController {

    private final OrderService orderService;
    private final MemberService memberService;

    @Autowired
    public OrderController(OrderService orderService, MemberService memberService) {
        this.orderService = orderService;
        this.memberService = memberService;
    }

    // ✅ 주문서 작성 화면
    @GetMapping
    public String showOrderForm(HttpSession session, Model model) {
        List<CartItem> cart = (List<CartItem>) session.getAttribute("cart");
        if (cart == null || cart.isEmpty()) {
            return "redirect:/cart";  // 장바구니 비었을 때
        }
        int totalPrice = 0;  // ✅ 여기에 추가!
        for (CartItem item : cart) {
            totalPrice += item.getPrice() * item.getQuantity();
        }
        model.addAttribute("cart", cart);
        model.addAttribute("totalPrice", totalPrice);
        return "order";  // 👉 order.html 보여줌
    }

    // ✅ 주문 제출 처리
    @PostMapping
    public String submitOrder(@RequestParam String address,
                              HttpSession session) {

        String loginUsername = (String) session.getAttribute("loginUser");
        Member member = memberService.findByUsername(loginUsername);

        List<CartItem> cart = (List<CartItem>) session.getAttribute("cart");
        if (cart == null || cart.isEmpty()) {
            return "redirect:/cart";
        }

        // 주문 저장
        orderService.saveOrder(member, address, cart);

        // 세션 장바구니 비우기
        session.removeAttribute("cart");

        return "redirect:/order/complete";
    }

    // ✅ 주문 완료 페이지
    @GetMapping("/complete")
    public String orderComplete() {
        return "order-complete";  // 👉 order-complete.html 필요
    }
}
