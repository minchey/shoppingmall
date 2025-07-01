package com.example.loginproject.domain; //폴더위치

import jakarta.persistence.*; // @entity, @id, @column 같은  JPA(자바 ORM) 관련 어노테이션 사용하기 위해 필요
import lombok.Getter; //lombok 라이브러리 - 자동으로 생성자/getter/setter 만들어주는 도구
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.AllArgsConstructor;

//클래스 선언부
@Entity //이 클래스는 DB테이블로 저장될 클래스 선언 (member 테이블로 매핑됨)
@Getter //모든 필드에 대한 getter 자동생성
@Setter //모든 필드에 대한 setter 자동생성
@NoArgsConstructor //기본 생성자 Member() 생성
@AllArgsConstructor //모든 필드를 받는 생성자 생성
public class Member {

    @Id //기본키
    @GeneratedValue(strategy = GenerationType.IDENTITY) //자동 증가하는 숫자로 설정 IDENTITY는 sql AUTO_INCREMENT랑 같다
    private Long id;

    @Column(nullable = false) //DB컬럼으로 생성되며 NULL값 허용하지 않음
    private String username;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String gender;

    @Column(nullable = false)
    private String phoneNumber;

    @Column(nullable = false)
    private String address;

    @Column(nullable = false)
    private String ssn;


    //ID없이 필요한 필드만 받아서 객체를 만들때 사용
    public Member(String username, String password, String name, String gender, String phoneNumber, String address, String ssn) {
        this.username = username;
        this.password = password;
        this.name = name;
        this.gender = gender;
        this.phoneNumber = phoneNumber;
        this.address = address;
        this.ssn = ssn;
    }
}

