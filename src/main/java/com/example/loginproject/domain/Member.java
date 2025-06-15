package com.example.loginproject.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String username;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String ssn;       // 주민등록번호

    @Column(nullable = false)
    private String phoneNumber;

    // 생성자 추가 가능
    public Member(String username, String password, String ssn, String phoneNumber) {
        this.username = username;
        this.password = password;
        this.ssn = ssn;
        this.phoneNumber = phoneNumber;
    }
}
