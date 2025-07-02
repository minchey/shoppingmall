package com.example.loginproject.repository; //리포지토리 위치 선언

import com.example.loginproject.domain.Post; //엔티티 사용
import org.springframework.data.jpa.repository.JpaRepository; //JPA가 기본 CRUD 기능을 사용할 수 있게 해줌

public interface PostRepository extends JpaRepository<Post, Long> {
//JpaRepository<Post, Long>을 상속 받아서 Post테이블을 조작할 수 있는 기본 기능 부여 받음
}

