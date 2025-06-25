package com.example.loginproject.domain;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "`orders`")   
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 주문자 (로그인된 사용자 기준)
    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;

    private String address;
    private LocalDateTime orderDate;

    private int totalPrice;

    // 주문에 포함된 상품들
    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    private List<OrderItem> orderItems;
}
