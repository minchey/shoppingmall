package com.example.loginproject.config; //서버에 업로드한 이미지나 파일을 브라우저에서 볼 수 있게 해주는 파일

import org.springframework.context.annotation.Configuration; //Configuration 어노테이션 설정
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry; //정적 리소스들(이미지,css,js,업로드된 파일)을 어떻게 처리할지 설정하는 필요한 클래스
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;//정적 리소스들(이미지,css,js,업로드된 파일)을 어떻게 처리할지 설정하는 필요한 클래스
import java.nio.file.Path; //실제 서버의 파일 시스템 경로를 가져오기 위한 유틸
import java.nio.file.Paths; //실제 서버의 파일 시스템 경로를 가져오기 위한 유틸


@Configuration //스프링 설정용 클래스 알림
public class WebConfig implements WebMvcConfigurer { //웹 관련 설정을 커스터마이징 하기 위한 설정클래스

    @Override //매서스 재정의
    public void addResourceHandlers(ResourceHandlerRegistry registry) { //경로지정 매서드 요청이 오면 실제 파일을 응답해주도록 처리하는 기능
        Path uploadDir = Paths.get("uploads");
        String uploadPath = uploadDir.toFile().getAbsolutePath();

        registry.addResourceHandler("/uploads/**")
                .addResourceLocations("file:" + uploadPath + "/");
    }
}
