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
                                @RequestParam Long postId) {
        commentService.deleteComment(id);
        return "redirect:/posts/" + postId;
    }
}
