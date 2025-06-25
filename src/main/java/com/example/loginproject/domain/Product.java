package com.example.loginproject.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Entity
@Getter
@NoArgsConstructor  // JPA 기본 생성자 필수!
@AllArgsConstructor
@Setter
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private int price;
    private String description;
    private String imageFilename;  // 파일명 저장

    // 직접 등록할 때 사용할 생성자
    public Product(String name, int price, String description) {
        this.name = name;
        this.price = price;
        this.description = description;
    }
    // Product.java (상품 엔티티에 추가)
    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;

    @ManyToOne
    @JoinColumn(name = "writer_id")
    private Member writer;


}
