package com.example.loginproject.config;

import org.springframework.context.annotation.Bean; //bean 어노테이션 사용
import org.springframework.context.annotation.Configuration; // configuration 어노테이션 사용
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder; //실제로 사용하는 암호화 클래스
import org.springframework.security.crypto.password.PasswordEncoder; //인터페이스 타입으로 반환 -> 스프링 di용

@Configuration //스프링이 감지해서 알아서 실행
public class AppConfig {

    @Bean //이 매서드가 반환하는 객체를 스프링 컨테이너에 등록
    public PasswordEncoder passwordEncoder() { //코드 변경의 유연함을 위해 만든 인터페이스. 나중에 return 만 변경해주면 됩
        return new BCryptPasswordEncoder(); //spring 기본 제공 암호화 코드. 단반향으로 복호화 불가능
    }
}
