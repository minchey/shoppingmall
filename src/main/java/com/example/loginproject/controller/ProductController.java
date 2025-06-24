package com.example.loginproject.controller;

import com.example.loginproject.domain.Product;
import com.example.loginproject.service.ProductService;
import com.example.loginproject.domain.Category;
import com.example.loginproject.repository.CategoryRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.UUID;
import java.util.List;

@Controller
public class ProductController {

    private final ProductService productService;
    private final CategoryRepository categoryRepository;

    @Autowired
    public ProductController(ProductService productService,
                             CategoryRepository categoryRepository) {
        this.productService = productService;
        this.categoryRepository = categoryRepository;
    }


    // 상품 등록 폼
    @GetMapping("/products/new")
    public String showCreateForm(Model model) {
        model.addAttribute("product", new Product());
        model.addAttribute("categories", categoryRepository.findAll()); // ✅ 추가
        return "product-form";
    }

    // 상품 등록 처리
    @PostMapping("/products/new")
    public String createProduct(@ModelAttribute Product product,
                                @RequestParam("categoryId") Long categoryId,
                                @RequestParam("imageFile") MultipartFile imageFile) throws IOException {

        if (!imageFile.isEmpty()) {
            String uploadDir = System.getProperty("user.dir") + "/uploads/";
            String originalFilename = imageFile.getOriginalFilename();
            String savedFileName = UUID.randomUUID() + "_" + originalFilename;

            File saveFile = new File(uploadDir, savedFileName);
            saveFile.getParentFile().mkdirs(); // 디렉토리 없으면 생성
            imageFile.transferTo(saveFile);

            product.setImageFilename(savedFileName);  // DB에 파일명 저장
        }
        Category category = categoryRepository.findById(categoryId).orElseThrow();
        product.setCategory(category);

        productService.saveProduct(product);
        return "redirect:/products";
    }


    // 상품 목록
    @GetMapping("/products")
    public String listProducts(Model model) {
        List<Product> productList = productService.getAllProducts();
        model.addAttribute("products", productList);
        return "products";
    }

    // 상품 상세보기
    @GetMapping("/products/{id}")
    public String viewProduct(@PathVariable Long id, Model model) {
        Product product = productService.getProductById(id);

        if (product == null) {
            return "redirect:/products";
        }

        model.addAttribute("product", product);
        return "product-detail";
    }
    @GetMapping("/products/search")
    public String searchProducts(@RequestParam String keyword, Model model) {
        List<Product> products = productService.searchProducts(keyword);
        model.addAttribute("products", products);
        return "products";
    }
    // 상품 삭제 처리
    @PostMapping("/products/{id}/delete")
    public String deleteProduct(@PathVariable Long id) {
        productService.deleteProductById(id);
        return "redirect:/products";
    }
}
