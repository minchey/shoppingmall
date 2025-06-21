package com.example.loginproject.controller;

import com.example.loginproject.domain.Comment;
import com.example.loginproject.domain.Post;
import com.example.loginproject.repository.PostRepository;
import com.example.loginproject.service.CommentService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
public class CommentController {

    @Autowired
    private CommentService commentService;

    @Autowired
    private PostRepository postRepository;

    @PostMapping("/comment/add")
    public String addComment(@RequestParam Long postId,
                             @RequestParam String content,
                             HttpSession session) {

        Post post = postRepository.findById(postId).orElseThrow();
        String author = (String) session.getAttribute("loginUser");

        Comment comment = new Comment();
        comment.setPost(post);
        comment.setContent(content);
        comment.setAuthor(author);

        commentService.saveComment(comment);
        return "redirect:/posts/" + postId;
    }

    @GetMapping("/comment/delete/{id}")
    public String deleteComment(@PathVariable Long id,
                                @RequestParam Long postId,
                                HttpSession session) {

        // 로그인한 사용자 가져오기
        String currentUser = (String) session.getAttribute("loginUser");

        // 댓글 찾아오기
        Comment comment = commentService.findById(id);
        System.out.println("현재 로그인 사용자: " + currentUser);
        System.out.println("댓글 작성자: " + comment.getAuthor());

        // 작성자 확인
        if (comment.getAuthor() == null || !comment.getAuthor().equals(currentUser)) {
            // 권한 없으면 삭제 안 하고 리다이렉트
            return "redirect:/posts/" + postId + "?error=unauthorized";
        }

        // 작성자 본인이면 삭제
        commentService.deleteComment(id);
        return "redirect:/posts/" + postId;
    }

}
