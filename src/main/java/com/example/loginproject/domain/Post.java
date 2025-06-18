// 게시글 엔티티 (DB 테이블 역할)
package com.example.loginproject.domain;

import jakarta.persistence.*;              // @Entity, @Id, @Column 등 JPA 관련
import lombok.*;                          // @Getter, @Setter, @NoArgsConstructor, @AllArgsConstructor

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String content;

    private String author;
}
