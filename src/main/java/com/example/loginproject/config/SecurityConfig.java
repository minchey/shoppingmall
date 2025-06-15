package com.example.loginproject.config; // ← 이거 꼭 있어야 함!

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import org.springframework.beans.factory.annotation.Autowired;

import com.example.loginproject.service.MemberService;


@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Autowired
    private MemberService memberService;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .authorizeHttpRequests()
                .requestMatchers("/register", "/login", "/check-username").permitAll()
                .anyRequest().authenticated()
                .and()
                .formLogin()
                .loginPage("/login")        // GET /login
                .loginProcessingUrl("/login") // POST /login 처리도 Security가 함
                .defaultSuccessUrl("/main", true)
                .failureUrl("/login?error")
                .and()
                .logout()
                .logoutUrl("/logout")
                .logoutSuccessUrl("/login");
        return http.build();
    }

  //  @Bean
   // public PasswordEncoder passwordEncoder() {
  //      return new BCryptPasswordEncoder();
   // }
}
