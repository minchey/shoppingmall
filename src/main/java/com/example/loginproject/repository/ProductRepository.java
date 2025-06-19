package com.example.loginproject.repository;

import com.example.loginproject.domain.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    // 기본적인 CRUD는 JpaRepository가 다 해줌!
}
