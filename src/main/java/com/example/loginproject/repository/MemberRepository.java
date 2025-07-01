package com.example.loginproject.repository; //repository는 db랑 직접 연결 되어있는 계층 데이터 조작하는곳

import com.example.loginproject.domain.Member; //member 엔티티 사용 이곳 객체가 실제 db테이블과 1:1 연결
import org.springframework.data.jpa.repository.JpaRepository; //스프링이 제공하는 JPA 인터페이스 기본 db작업 구현되어있음

import java.util.Optional; //값이 있을수도 없을수도 있다는 명확하게 표현하는 자료형

public interface MemberRepository extends JpaRepository<Member, Long> { //JPA가 자동으로 구현체를 만들어주는 인터페이 member - 테이블명, long - id의 자료형
    Optional<Member> findByUsername(String username); //커스텀 매서드 정의 (자동쿼리생성) username으로 회원찾기
    //optional인 이유는 회원이 있으면 값을 주고 없으면 빈값을 줌
}