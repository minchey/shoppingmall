package com.example.loginproject.controller; //패키지 선언

import com.example.loginproject.domain.Post; //Post 엔티티 사용
import com.example.loginproject.repository.PostRepository;//PostRepository 사용 db에 저장/조회하는 인터페이스
import org.springframework.beans.factory.annotation.Autowired; //의존성 자동 주입
import org.springframework.stereotype.Controller; //웹요청을 처리하는 컨트롤러 선언 어노테이션
import org.springframework.ui.Model; //데이터를 html에 전달할 때 사용
import org.springframework.web.bind.annotation.*; //요청관련 어노테이션
import org.springframework.web.multipart.MultipartFile; //파일 업로드시 파일 데이터를 받기 위한 클래스
import java.io.File; //파일 저장과
import java.io.IOException;//UUID 생성을
import java.util.UUID; //위한 자바 기본 클래스
import com.example.loginproject.service.CommentService; //댓글기능
import com.example.loginproject.repository.CommentRepository; //댓글기능
import jakarta.servlet.http.HttpSession;//로그인 사용자 저장을 위한 세션


@Controller //요청 처리하는 컨트롤러라고 선언
@RequestMapping("/posts") // posts로 시작하는 요청을 모두 처리
public class PostController {

    private final PostRepository postRepository; //게시글 crud용 기능
    private final CommentService commentService; //댓글 조회기능
    private final CommentRepository commentRepository; //댓글 삭제 기능

    @Autowired //위의 기능들의 생성자 자동 연결
    public PostController(PostRepository postRepository, CommentService commentService, CommentRepository commentRepository) {
        this.postRepository = postRepository;
        this.commentService = commentService;
        this.commentRepository = commentRepository;
    }

    // 게시글 목록 보기
    @GetMapping // posts로 접속하면 모든 게시글을 조회해서 postList.html로 전달
    public String listPosts(Model model) {
        model.addAttribute("posts", postRepository.findAll());
        return "postList";  // → templates/postList.html
    }

    // 글쓰기 폼
    @GetMapping("/new") //posts/new로 접속하면 글 작성 페이지 보여줌 postForm.html
    public String newPostForm() {
        return "postForm"; // → templates/postForm.html
    }

    // 글 작성 처리
    @PostMapping //post방식으로 데이터를 보낼 때 처리 해줌 등록버튼 누를때 실행
    public String createPost( //폼에서 title, content, file 값을 받음
            @RequestParam String title, //RequestParam = html에서 <input name = "title"> 처럼 name으로 들어온 값을 가져옴
            @RequestParam String content,
            @RequestParam("file") MultipartFile file //업로드한 파일 객체
    ) throws IOException { //예외처리

        Post post = new Post(); //새로운 포스트 객체 생성 입력받은 제목과 내용을 post객체에 저장
        post.setTitle(title);
        post.setContent(content);

        // 파일이 있을 경우 저장 처리
        if (!file.isEmpty()) {
            String uploadDir = System.getProperty("user.dir") + "/uploads/"; //uploads 폴더에 파일저장
            String originalFilename = file.getOriginalFilename(); //사용자가 올린 파일 이름
            String uuid = UUID.randomUUID().toString(); //파일이름 겹치지 않게 uuid를 앞에 붙여 저장
            String storedFilename = uuid + "_" + originalFilename;

            File saveFile = new File(uploadDir, storedFilename); //실제로 저장할 파일명과 경로 지정
            saveFile.getParentFile().mkdirs(); // 폴더 없으면 생성
            file.transferTo(saveFile); //사용자가 업로드한 파일을 지정한 경로에 저장

            // Post에 파일 정보 저장
            post.setOriginalFilename(originalFilename); //원본 파일명과 uuid 붙은 파일명 둘다 저장
            post.setStoredFilename(storedFilename);
            post.setIsImage(originalFilename.matches("(?i).+\\.(png|jpg|jpeg|gif)$")); //정규표현식으로 이미지면 true 아니면 false
        }

        postRepository.save(post); //글 내용 파일 정보까지 저장된 post 객체를 db에 저장
        return "redirect:/posts"; //완료 후 목록으로
    }


    // 게시글 상세 보기
    @GetMapping("{id}") //posts/id로 접근하면 id에 맞는 게시글을 찾아서 보여줌
    public String viewPost(@PathVariable Long id, //PathVariable = {id} 값을 변수 id로 가져옴
                           Model model, //html에 데이터를 실어 보내는 바구니
                           HttpSession session) { //로그인 사용자 정보를 확인하거나 전달하는 세션객체
        Post post = postRepository.findById(id).orElse(null); //db에서 id에 해당하는 게시글 가져오거나 없으면 null
        if (post == null) {
            return "redirect:/posts"; // id가 null이면 목록화면으로
        }

        // ✅ 조회수 증가
        post.setViewCount(post.getViewCount() + 1); //조회수 1증가 post에 setter가 자동으로 매서스 생성해줌
        postRepository.save(post); // DB에 반영

        model.addAttribute("post", post); //게시글을 post라는 이름으로 html에 전달
        model.addAttribute("comments", commentService.getCommentsByPost(post)); //해당 게시글에 달린 댓글 목록을 comments라는 이름으로 넘겨줌

        String loginUser = (String) session.getAttribute("loginUser");//현재 로그인한 사용자를 세션에서 꺼내서 LoginUser로 넘겨줌
        model.addAttribute("loginUser", loginUser);

        return "postDetail"; //templates/postDetail.html 보여줌
    }

    //게시글 수정
    @GetMapping("/{id}/edit") //게시글 수정 url
    public String editPostForm(@PathVariable Long id,//id값 변수로 가져옴
                               Model model) { //데이터를 넘겨줄 객체
        Post post = postRepository.findById(id).orElse(null); //해당 게시글 가져오는 코드 없다면 null반환
        model.addAttribute("post", post);//post라는 이름으로 방금 찾은 게시글을 모델에 추가
        return "postEdit";  // templates/postEdit.html로 이동
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
