package com.example.loginproject.controller;

import com.example.loginproject.domain.Product;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class ProductController {

    // 임시 상품 리스트 (DB 없을 때 테스트용)
    private final List<Product> productList = List.of(
            new Product(1L, "청바지", 45000, "편안한 청바지"),
            new Product(2L, "운동화", 69000, "가벼운 러닝화"),
            new Product(3L, "후드티", 35000, "겨울용 기모 후드티")
    );

    // 상품 목록 페이지
    @GetMapping("/products")
    public String showProductList(Model model) {
        model.addAttribute("products", productList);
        return "products";
    }

    @GetMapping("/products/{id}")
    public String showProductDetail(@PathVariable Long id, Model model) {
        List<Product> productList = List.of(
                new Product(1L, "청바지", 45000, "편안한 청바지"),
                new Product(2L, "운동화", 69000, "가벼운 러닝화"),
                new Product(3L, "후드티", 35000, "겨울용 기모 후드티")
        );

        // id로 상품 찾기
        Product foundProduct = productList.stream()
                .filter(p -> p.getId().equals(id))
                .findFirst()
                .orElse(null);

        if (foundProduct == null) {
            return "redirect:/products"; // 없는 상품이면 목록으로 이동
        }

        model.addAttribute("product", foundProduct);
        return "product-detail"; // templates/product-detail.html로 이동
    }

}
