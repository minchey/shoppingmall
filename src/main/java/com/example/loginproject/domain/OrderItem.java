package com.example.loginproject.domain;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class OrderItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String productName;
    private int price;
    private int quantity;
    private String imageFilename;

    @ManyToOne
    @JoinColumn(name = "order_id")
    private Order order;
}
