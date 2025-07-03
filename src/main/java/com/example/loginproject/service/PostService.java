package com.example.loginproject.service; //서비스 경로

import com.example.loginproject.domain.Post; //엔티티 사용
import com.example.loginproject.repository.PostRepository;//리포지토리 사용
import org.springframework.beans.factory.annotation.Autowired;//Autowired 어노테이션 사용
import org.springframework.stereotype.Service;//서비스 역할인것을 알려줌

import java.util.List;//여러개의 데이터를 저장하는 자료구조 List<Post>형태로 사용

@Service //서비스 역할이라는걸 선언
public class PostService { //게시글 관련 기능(저장,조회 등)을 담당하는 클래스

    @Autowired //리포지토리 객체 자동으로 넣어줌
    private PostRepository postRepository; //db에 저장하고 불러오는 역할

    public void savePost(Post post) {
        postRepository.save(post);
    } //사용자가 작성한 게시글을 받아서 db에 저장하는 매서드
        //JPA에서 제공하는 기능 새 게시글은 INSERT, 수정은 UPDATE
    public List<Post> getAllPosts() {
        return postRepository.findAll();
    }//여러개의 게시글을 저장하는 리스트
}
