package com.example.loginproject.domain;

public class Product {
    private Long id;
    private String name;
    private int price;
    private String description;

    // 생성자
    public Product(Long id, String name, int price, String description) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.description = description;
    }

    // Getter만 있어도 충분함 (Lombok 써도 OK)
    public Long getId() { return id; }
    public String getName() { return name; }
    public int getPrice() { return price; }
    public String getDescription() { return description; }
}
