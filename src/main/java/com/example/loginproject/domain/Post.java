// 게시글 엔티티 (DB 테이블 역할)
package com.example.loginproject.domain;

import jakarta.persistence.*;              // @Entity, @Id, @Column 등 JPA 관련
import lombok.*;                          // @Getter, @Setter, @NoArgsConstructor, @AllArgsConstructor
import java.time.LocalDateTime; //작성시간을 저장할 필드 createAT에서 사용되는 시간 자료형을 위한 import
import org.hibernate.annotations.CreationTimestamp; //entity가 실행될때 자동으로 현재 시간을 넣어주는 어노테이션 사용

@Entity //entity클래스 선언
@Getter //모든 필드에 대한 get 메서드 자동생성
@Setter //모든 필드에 대한 set 매서드 자동생성
@NoArgsConstructor //기본생성자
@AllArgsConstructor //모든 필드를 받는 생성자

public class Post { //게시글 정보를 담는 post클래스 생성. db에서 행

    @Id //기본키
    @GeneratedValue(strategy = GenerationType.IDENTITY) //자동으로 1씩 증가
    private Long id;

    @Column(nullable = false) //title컬럼 null불가
    private String title;

    @Column(columnDefinition = "TEXT", nullable = false) //content는 글 내용이라 text 자료형으로 설정
    private String content;

    private String author; //작성자 이름 null 허용

    private String originalFilename;   // 사용자가 업로드한 파일 이름
    private String storedFilename;     // 서버에 저장된 실제 파일 이름
    private boolean isImage;           // 이미지인지 여부

    public void setOriginalFilename(String originalFilename) {
        this.originalFilename = originalFilename;
    } //사용자가 업로드한 파일 이름을 post 객체에 저장

    public void setStoredFilename(String storedFilename) {
        this.storedFilename = storedFilename;
    } //서버에 저장한 진짜 파일이름을 설정

    public void setIsImage(boolean isImage) {
        this.isImage = isImage;
    } //업로드한 파일이 이미지인지 아닌지 판단
    @CreationTimestamp
    private LocalDateTime createdAt;  // 글을 db에 저장할 때 작성일 자동 저장

    @Column(nullable = false) //조회수 기본값 0 null 불가
    private int viewCount = 0;


}
