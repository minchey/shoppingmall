package com.example.loginproject.controller;

import com.example.loginproject.domain.Post;
import com.example.loginproject.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.io.File;
import java.io.IOException;
import java.util.UUID;
import com.example.loginproject.service.CommentService;
import com.example.loginproject.repository.CommentRepository;
import jakarta.servlet.http.HttpSession;


@Controller
@RequestMapping("/posts")
public class PostController {

    private final PostRepository postRepository;
    private final CommentService commentService;
    private final CommentRepository commentRepository;

    @Autowired
    public PostController(PostRepository postRepository, CommentService commentService, CommentRepository commentRepository) {
        this.postRepository = postRepository;
        this.commentService = commentService;
        this.commentRepository = commentRepository;
    }

    // 게시글 목록 보기
    @GetMapping
    public String listPosts(Model model) {
        model.addAttribute("posts", postRepository.findAll());
        return "postList";  // → templates/postList.html
    }

    // 글쓰기 폼
    @GetMapping("/new")
    public String newPostForm() {
        return "postForm"; // → templates/postForm.html
    }

    // 글 작성 처리
    @PostMapping
    public String createPost(
            @RequestParam String title,
            @RequestParam String content,
            @RequestParam("file") MultipartFile file
    ) throws IOException {

        Post post = new Post();
        post.setTitle(title);
        post.setContent(content);

        // 파일이 있을 경우 저장 처리
        if (!file.isEmpty()) {
            String uploadDir = System.getProperty("user.dir") + "/uploads/";
            String originalFilename = file.getOriginalFilename();
            String uuid = UUID.randomUUID().toString();
            String storedFilename = uuid + "_" + originalFilename;

            File saveFile = new File(uploadDir, storedFilename);
            saveFile.getParentFile().mkdirs(); // 폴더 없으면 생성
            file.transferTo(saveFile);

            // Post에 파일 정보 저장
            post.setOriginalFilename(originalFilename);
            post.setStoredFilename(storedFilename);
            post.setIsImage(originalFilename.matches("(?i).+\\.(png|jpg|jpeg|gif)$"));
        }

        postRepository.save(post);
        return "redirect:/posts";
    }


    // 게시글 상세 보기
    @GetMapping("{id}")
    public String viewPost(@PathVariable Long id, Model model, HttpSession session) {
        Post post = postRepository.findById(id).orElse(null);
        if (post == null) {
            return "redirect:/posts"; // 또는 오류 페이지로 보내기
        }
        model.addAttribute("post", post);
        model.addAttribute("comments", commentService.getCommentsByPost(post));

        String loginUser = (String) session.getAttribute("loginUser");
        model.addAttribute("loginUser", loginUser);
        return "postDetail"; // → templates/postDetail.html
    }
    //게시글 수정
    @GetMapping("/{id}/edit")
    public String editPostForm(@PathVariable Long id, Model model) {
        Post post = postRepository.findById(id).orElse(null);
        model.addAttribute("post", post);
        return "postEdit";  // templates/postEdit.html
    }

    @PostMapping("/{id}/edit")
    public String updatePost(@PathVariable Long id,
                             @RequestParam String title,
                             @RequestParam String content) {
        Post post = postRepository.findById(id).orElse(null);
        if (post != null) {
            post.setTitle(title);
            post.setContent(content);
            postRepository.save(post);  // 업데이트
        }
        return "redirect:/posts/" + id;
    }

    //게시글 삭제
    @PostMapping("/{id}/delete")
    public String deletePost(@PathVariable Long id) {
        commentRepository.deleteByPostId(id);
        postRepository.deleteById(id);
        return "redirect:/posts";
    }


}
