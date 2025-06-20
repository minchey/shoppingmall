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

    private String originalFilename;   // 사용자가 업로드한 파일 이름
    private String storedFilename;     // 서버에 저장된 실제 파일 이름
    private boolean isImage;           // 이미지인지 여부

    public void setOriginalFilename(String originalFilename) {
        this.originalFilename = originalFilename;
    }

    public void setStoredFilename(String storedFilename) {
        this.storedFilename = storedFilename;
    }

    public void setIsImage(boolean isImage) {
        this.isImage = isImage;
    }

}
