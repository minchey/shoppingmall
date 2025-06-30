package com.example.loginproject.controller; //패키지 경로선언

import com.example.loginproject.service.MemberService; //memberservice를 사용하기 위해 선언 - 회원가입 로직 존재
import org.springframework.beans.factory.annotation.Autowired; //스프링이 memberservice를 자동으로 주입하기 위한 어노테이션
import org.springframework.stereotype.Controller; //스프링에 컨트롤러 라고 전달
import org.springframework.web.bind.annotation.*; //웹 요청을 받기위한 어노테이션 모음
import jakarta.servlet.http.HttpSession; //사용자 로그인 정보를 저장하는 세션 객체
import org.springframework.ui.Model; //서버에서 화면으로 데이터를 넘겨주는 도구
import com.example.loginproject.dto.MemberRequest;                // DTO - 회원가입 할때 받는 정보 담는 DTO
import jakarta.validation.Valid;                                   // 유효성 검사
import org.springframework.validation.BindingResult;              // 에러 담는 그릇


@Controller
public class MemberController { //웹 요청을 받는 클래스 선언

    private final MemberService memberService; //실제 비즈니스 로직을 처리하는 memberservice를 사용할 준비

    @Autowired //스프링이 memberservice 자동으로 의존성 주입 -DI
    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }

    @GetMapping("/register") //사용자가 /register url을 get 요청을 하면 빈 DTO를 모델이 담아 register.html로 보내줌
    public String registerForm(Model model) {
        model.addAttribute("memberRequest", new MemberRequest()); // 이거 필수
        return "register";
    }

    @PostMapping("/register") //post방식으로 제출된 폼 받기
    public String register(
            @Valid @ModelAttribute MemberRequest memberRequest, //valid로 유효성 검사
            BindingResult bindingResult, // 에러 담는 그릇
            Model model //화면에 메시지 보내는 용도
    ) {
        if (bindingResult.hasErrors()) { //유효성 검사 에러가 있으면 다시 폼으로
            return "register";
        }

        if (!memberRequest.getPassword().equals(memberRequest.getConfirmPassword())) { //비밀번호가 일치 하지 않을때 에러
            model.addAttribute("error", "비밀번호가 일치하지 않습니다.");
            return "register";
        }

        if (memberService.isUsernameDuplicate(memberRequest.getUsername())) { // 아이디 중복 - 회원가입 제출시 서버에 확인
            model.addAttribute("error", "이미 사용 중인 아이디입니다.");
            return "register";
        }

        memberService.register( //유효성 검사 통과하면 각 객체에 데이터 담기
                memberRequest.getUsername(),
                memberRequest.getPassword(),
                memberRequest.getName(),
                memberRequest.getGender(),
                memberRequest.getPhoneNumber(),
                memberRequest.getAddress(),
                memberRequest.getSsn()
        );


        return "redirect:/login"; //가입완료 후 로그인페이지
    }
    @GetMapping("/check-username") //아이디 중복확인 - 실시간으로 사용자 편의
    @ResponseBody
    public String checkUsername(@RequestParam String username) {
       // System.out.println("🔥 [중복확인 요청] username = " + username);
        boolean isDuplicate = memberService.isUsernameDuplicate(username); //memberservice에 보내서 중복확인 후 isDuplicate에 ture or false 저장
        return isDuplicate ? "duplicate" : "ok";
    }


    @GetMapping("/login")
    public String loginForm() {
        return "login"; // 로그인 폼 페이지 보여줌
    }


    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate(); // 세션 전체 제거 (로그아웃)
        return "redirect:/login"; // 로그인 페이지로 이동
    }


}
