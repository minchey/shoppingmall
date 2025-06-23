package com.example.loginproject.util;

import com.example.loginproject.domain.Category;
import com.example.loginproject.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class CategoryDataLoader implements CommandLineRunner {

    private final CategoryRepository categoryRepository;

    @Override
    public void run(String... args) {
        if (categoryRepository.count() == 0) {
            categoryRepository.saveAll(List.of(
                    new Category(null, "의류"),
                    new Category(null, "가방"),
                    new Category(null, "전자제품"),
                    new Category(null, "신발")
            ));
        }
    }
}
