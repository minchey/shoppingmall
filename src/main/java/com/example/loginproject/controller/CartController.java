package com.example.loginproject.controller;

import com.example.loginproject.domain.Product;
import com.example.loginproject.dto.CartItem;
import com.example.loginproject.service.ProductService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/cart")
public class CartController {

    private final ProductService productService;

    @Autowired
    public CartController(ProductService productService) {
        this.productService = productService;
    }

    // ✅ 장바구니 담기
    @PostMapping("/add")
    public String addToCart(@RequestParam Long productId, HttpSession session) {
        // 세션에서 장바구니 가져오기
        List<CartItem> cart = (List<CartItem>) session.getAttribute("cart");
        if (cart == null) {
            cart = new ArrayList<>();
        }

        // 상품 정보 가져오기
        Product product = productService.getProductById(productId);
        if (product == null) {
            return "redirect:/products";  // 잘못된 상품 ID일 경우
        }

        // 장바구니에 이미 담겨있으면 수량 +1
        boolean found = false;
        for (CartItem item : cart) {
            if (item.getProductId().equals(productId)) {
                item.setQuantity(item.getQuantity() + 1);
                found = true;
                break;
            }
        }

        // 없으면 새로 추가
        if (!found) {
            CartItem item = new CartItem();
            item.setProductId(product.getId());
            item.setProductName(product.getName());
            item.setPrice(product.getPrice());
            item.setQuantity(1);
            item.setImageFilename(product.getImageFilename());
            cart.add(item);
        }

        // 세션에 장바구니 저장
        session.setAttribute("cart", cart);

        return "redirect:/cart";
    }

    // ✅ 장바구니 보기
    @GetMapping
    public String viewCart(HttpSession session, Model model) {
        List<CartItem> cart = (List<CartItem>) session.getAttribute("cart");
        if (cart == null) {
            cart = new ArrayList<>();
        }

        int totalPrice = 0;
        for (CartItem item : cart) {
            totalPrice += item.getPrice() * item.getQuantity();
        }

        model.addAttribute("cart", cart);
        model.addAttribute("totalPrice", totalPrice);
        return "cart";
    }

    //장바구니 수량 변경
    @PostMapping("/update")
    public String updateQuantity(@RequestParam Long productId,
                                 @RequestParam int quantity,
                                 HttpSession session) {

        List<CartItem> cart = (List<CartItem>) session.getAttribute("cart");
        if (cart != null) {
            for (CartItem item : cart) {
                if (item.getProductId().equals(productId)) {
                    item.setQuantity(quantity);
                    break;
                }
            }
        }
        return "redirect:/cart";
    }

    //장바구니 삭제기능
    @PostMapping("/delete")
    public String deleteFromCart(@RequestParam Long productId, HttpSession session) {
        List<CartItem> cart = (List<CartItem>) session.getAttribute("cart");
        if (cart != null) {
            cart.removeIf(item -> item.getProductId().equals(productId));
        }
        return "redirect:/cart";
    }



}
