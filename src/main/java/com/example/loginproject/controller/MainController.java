package com.example.loginproject.controller; //패키지 선언

import org.springframework.stereotype.Controller; //컨트롤러 어노테이션 사용
import org.springframework.web.bind.annotation.GetMapping; //get요청을 하기 위한 getmapping 어노테이션 설정
import jakarta.servlet.http.HttpSession; // httpsession 사용 로그인한 사용자 정보 가져오게 함
import org.springframework.ui.Model; //model 객체를 쓰기 위해 선언 백엔드에서 html로 데이터 보내주는데 사용

@Controller
public class MainController { //메인페이지 관련 요청을 처리하는 클래스

    @GetMapping("/main")  //사용자가 /main 주소로 접속하면 아래 매서드 실행 하라는듯
    public String mainPage(HttpSession session, Model model) { //httpsession session - 로그인한 사용장 세션정보 가져옴  model model - html로 값 전달할때 쓰는 상자
        String loginUser = (String) session.getAttribute("loginUser"); //세션에서 loginuser라는 이름으로 저장된 값을 꺼내 변수에 대입
        model.addAttribute("loginUser", loginUser); //html뷰로 값을 넘김 luginuser라는 이름으 템플릿에 전달. ${loginuser}로 출력가능
        return "main"; //main.html 화면 보여 달라는 코드
    }
}
