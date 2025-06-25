package com.example.loginproject.service;

import com.example.loginproject.domain.Product;
import com.example.loginproject.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

@Service
public class ProductService {

    private final ProductRepository productRepository;

    @Autowired  // 생성자 자동 주입
    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    // 상품 저장
    public void saveProduct(Product product) {
        productRepository.save(product);
    }

    // 상품 목록 조회
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    // 상품 1개 조회 (id 기준)
    public Product getProductById(Long id) {
        return productRepository.findById(id).orElse(null);
    }
    public List<Product> searchProducts(String keyword) {
        return productRepository.findByNameContainingIgnoreCaseOrCategory_NameContainingIgnoreCase(keyword, keyword);
    }
    public void deleteProductById(Long id) {
        productRepository.deleteById(id);
    }
    public Page<Product> getProductPage(Pageable pageable) {
        return productRepository.findAll(pageable);  // JPA 기본 제공
    }
}
