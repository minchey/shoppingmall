package com.example.loginproject.repository; //리포지토리 위치 선언

import com.example.loginproject.domain.Post; //엔티티 사용
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Long> {

}

