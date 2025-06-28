//로그인 로그아웃 페이지접근 권한 설정

package com.example.loginproject.config;

import com.example.loginproject.service.MemberService; //memberservice 클래스 사용을 위해
import jakarta.servlet.http.HttpServletRequest; //서블릿 요청
import jakarta.servlet.http.HttpServletResponse; //서블릿 응답
import org.springframework.http.HttpMethod; //http 요청방 구분 post / get 등
import org.springframework.context.annotation.Bean; //어노테이션
import org.springframework.context.annotation.Configuration; //어노테이션
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity; //spring security를 사용하기 위해, @EnableWebSecurity 없으면 보안설정 자체가 적용 X
import org.springframework.security.web.SecurityFilterChain; //보안필터체인 설정 filterChain(HttpSecurity http)에서 사용
import org.springframework.security.config.Customizer; //security 기본설정을 위해
import org.springframework.security.config.annotation.web.builders.HttpSecurity; //어떤 url에 어떤 인증/인가가 필요한 정의 중요힘
import org.springframework.beans.factory.annotation.Autowired;

@Configuration //스프링 설정클래스 명시
@EnableWebSecurity //Spring Security 활성화
public class SecurityConfig {

    @Autowired  //memberservice 자동 사용
    private MemberService memberService;


    @Bean //스프링 컨테이너에 등록(필수)
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {//스프링시큐리티 필서 설정집합
        return http //http 객체를 체이닝 방식으 이어서 구성
                .csrf(Customizer.withDefaults()) //csrf 기본설정 활성화 (크로스사이트 요청위조 방지), 폼 전송시 보안토큰 확인으로 공격방지
                .authorizeHttpRequests(auth -> auth //요청 url에 따라 접근 권한 설정시작
                        .requestMatchers("/register", "/login", "/check-username", "/main", "/", "/css/**", "/js/**").permitAll() //누구나 접근 가능한 url경로 설정
                        .requestMatchers(HttpMethod.GET, "/products/**").permitAll() //로그인 안해도 방문가능
                        .requestMatchers(HttpMethod.POST, "/products/*/delete").authenticated() //상품삭제 요청은 로그인한 사용자만 요청 가능
                        .anyRequest().authenticated() //나머지 모든 요청은 로그인 필수
                )
                .formLogin(form -> form //로그인방식 설정 시작
                        .loginPage("/login") //로그인 페이지 url
                        .loginProcessingUrl("/login") //로그인 폼 action 처리 경로
                        .successHandler((request, response, authentication) -> {
                            String username = authentication.getName();
                            request.getSession().setAttribute("loginUser", username); //세션에 사용자 이름 저장
                            response.sendRedirect("/main"); //로그인 성공시 이동 url
                        })
                        .failureUrl("/login?error") //로그인 실패시 이동 url
                )
                .logout(logout -> logout //-> 이건 람다식
                        .logoutUrl("/logout") //로그아웃 url
                        .logoutSuccessUrl("/main") //로그아웃 후 이동할 페이지
                )
                .build(); //위 설정을 바탕으로 SecurityFilterChain 객체를 반환
    }
}
