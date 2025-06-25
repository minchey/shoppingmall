package com.example.loginproject.service;

import com.example.loginproject.domain.*;
import com.example.loginproject.dto.CartItem;
import com.example.loginproject.repository.OrderRepository;
import com.example.loginproject.repository.OrderItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;

    @Autowired
    public OrderService(OrderRepository orderRepository,
                        OrderItemRepository orderItemRepository) {
        this.orderRepository = orderRepository;
        this.orderItemRepository = orderItemRepository;
    }

    public void saveOrder(Member member, String address, List<CartItem> cartItems) {
        Order order = new Order();
        order.setMember(member);
        order.setAddress(address);
        order.setOrderDate(LocalDateTime.now());

        // OrderItem으로 변환
        List<OrderItem> orderItemList = new ArrayList<>();
        int totalPrice = 0;

        for (CartItem cartItem : cartItems) {
            OrderItem item = new OrderItem();
            item.setProductName(cartItem.getProductName());
            item.setPrice(cartItem.getPrice());
            item.setQuantity(cartItem.getQuantity());
            item.setImageFilename(cartItem.getImageFilename());
            item.setOrder(order); // FK 설정
            orderItemList.add(item);

            totalPrice += cartItem.getPrice() * cartItem.getQuantity();
        }

        order.setTotalPrice(totalPrice);
        order.setOrderItems(orderItemList);

        // Cascade 옵션 덕분에 orderItem도 같이 저장됨
        orderRepository.save(order);
    }
    public List<Order> getOrdersByMember(Member member) {
        return orderRepository.findByMember(member);
    }
}
