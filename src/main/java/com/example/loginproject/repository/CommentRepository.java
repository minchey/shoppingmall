package com.example.loginproject.repository;

import com.example.loginproject.domain.Comment;
import com.example.loginproject.domain.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {

    // 특정 게시글(Post)에 달린 댓글들 가져오기
    List<Comment> findByPost(Post post);
}
