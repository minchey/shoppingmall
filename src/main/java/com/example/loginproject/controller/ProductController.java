package com.example.loginproject.controller;

import com.example.loginproject.domain.Product;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class ProductController {

    @GetMapping("/products")
    public String showProductList(Model model) {
        List<Product> productList = List.of(
                new Product(1L, "청바지", 45000, "편안한 청바지"),
                new Product(2L, "운동화", 69000, "가벼운 러닝화"),
                new Product(3L, "후드티", 35000, "겨울용 기모 후드티")
        );

        model.addAttribute("products", productList);
        return "products"; // templates/products.html 로 이동
    }
 
}
