package com.example.loginproject.domain;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;
import org.hibernate.annotations.CreationTimestamp;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String content; // 댓글 내용

    private String author;  // 작성자 이름

    @CreationTimestamp
    private LocalDateTime createdAt; // 작성일 자동 저장

    @ManyToOne
    @JoinColumn(name = "post_id")
    private Post post; // 어떤 게시글에 달린 댓글인지
}
