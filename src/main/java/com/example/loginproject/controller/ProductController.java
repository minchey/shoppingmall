package com.example.loginproject.controller;

import com.example.loginproject.domain.Product;
import com.example.loginproject.service.ProductService;
import com.example.loginproject.domain.Category;
import com.example.loginproject.repository.CategoryRepository;
import com.example.loginproject.domain.Member;
import com.example.loginproject.service.MemberService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import jakarta.servlet.http.HttpSession;

import java.io.File;
import java.io.IOException;
import java.util.UUID;
import java.util.List;

@Controller
public class ProductController {

    private final ProductService productService;
    private final CategoryRepository categoryRepository;
    private final MemberService memberService;

    @Autowired
    public ProductController(ProductService productService,
                             CategoryRepository categoryRepository,
                             MemberService memberService) {
        this.productService = productService;
        this.categoryRepository = categoryRepository;
        this.memberService = memberService;
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
                                @RequestParam("imageFile") MultipartFile imageFile,
                                HttpSession session) throws IOException {

        // 🔒 로그인 사용자 세션에서 가져오기
        String loginUsername = (String) session.getAttribute("loginUser");


        // 작성자 정보 조회
        Member writer = memberService.findByUsername(loginUsername); // 반드시 MemberService에 이 메서드 존재해야 함
        product.setWriter(writer);  // 작성자 설정

        // 이미지 처리
        if (!imageFile.isEmpty()) {
            String uploadDir = System.getProperty("user.dir") + "/uploads/";
            String originalFilename = imageFile.getOriginalFilename();
            String savedFileName = UUID.randomUUID() + "_" + originalFilename;

            File saveFile = new File(uploadDir, savedFileName);
            saveFile.getParentFile().mkdirs(); // 디렉토리 없으면 생성
            imageFile.transferTo(saveFile);

            product.setImageFilename(savedFileName);  // DB에 파일명 저장
        }

        // 카테고리 설정
        Category category = categoryRepository.findById(categoryId).orElseThrow();
        product.setCategory(category);

        // 저장
        productService.saveProduct(product);
        return "redirect:/products";
    }



    // 상품 목록
    @GetMapping("/products")
    public String listProducts(Model model,
                               @RequestParam(defaultValue = "0") int page) {

        // 1페이지에 50개, 최신순 정렬
        Pageable pageable = PageRequest.of(page, 50, Sort.by("id").descending());
        Page<Product> productPage = productService.getProductPage(pageable);  // 서비스에서 Page 객체 반환

        model.addAttribute("productPage", productPage);
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
