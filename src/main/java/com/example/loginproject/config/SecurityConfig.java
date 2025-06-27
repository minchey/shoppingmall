//로그인 로그아웃 페이지접근 권한 설정

package com.example.loginproject.config;

import com.example.loginproject.service.MemberService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpMethod;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.beans.factory.annotation.Autowired;

@Configuration //스프링 설정클래스 명시
@EnableWebSecurity //Spring Security 활성화
public class SecurityConfig {

    @Autowired
    private MemberService memberService;


    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return http
                .csrf(Customizer.withDefaults())
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/register", "/login", "/check-username", "/main", "/", "/css/**", "/js/**").permitAll()
                        .requestMatchers(HttpMethod.GET, "/products/**").permitAll() //로그인 안해도 방문가능
                        .requestMatchers(HttpMethod.POST, "/products/*/delete").authenticated()
                        .anyRequest().authenticated()
                )
                .formLogin(form -> form
                        .loginPage("/login")
                        .loginProcessingUrl("/login")
                        .successHandler((request, response, authentication) -> {
                            String username = authentication.getName();
                            request.getSession().setAttribute("loginUser", username);
                            response.sendRedirect("/main");
                        })
                        .failureUrl("/login?error")
                )
                .logout(logout -> logout
                        .logoutUrl("/logout")
                        .logoutSuccessUrl("/main")
                )
                .build();
    }
}
