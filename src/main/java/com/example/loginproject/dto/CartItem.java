package com.example.loginproject.dto;

public class CartItem {
    private Long productId;
    private String productName;
    private int quantity;
    private int price;
    private String imageFilename;

    // 기본 생성자
    public CartItem() {}

    // 전체 생성자
    public CartItem(Long productId, String productName, int quantity, int price, String imageFilename) {
        this.productId = productId;
        this.productName = productName;
        this.quantity = quantity;
        this.price = price;
        this.imageFilename = imageFilename;
    }

    // Getter / Setter
    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getImageFilename() {
        return imageFilename;
    }

    public void setImageFilename(String imageFilename) {
        this.imageFilename = imageFilename;
    }
}
