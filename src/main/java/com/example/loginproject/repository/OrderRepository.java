package com.example.loginproject.repository;

import com.example.loginproject.domain.Order;
import com.example.loginproject.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order> findByMember(Member member);

}

