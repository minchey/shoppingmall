// CategoryRepository.java
package com.example.loginproject.repository;

import com.example.loginproject.domain.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long> {
}
