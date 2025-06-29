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
public class MemberController {

    private final MemberService memberService;

    @Autowired
    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }

    @GetMapping("/register")
    public String registerForm(Model model) {
        model.addAttribute("memberRequest", new MemberRequest()); // 이거 필수
        return "register";
    }

    @PostMapping("/register")
    public String register(
            @Valid @ModelAttribute MemberRequest memberRequest,
            BindingResult bindingResult,
            Model model
    ) {
        if (bindingResult.hasErrors()) {
            return "register";
        }

        if (!memberRequest.getPassword().equals(memberRequest.getConfirmPassword())) {
            model.addAttribute("error", "비밀번호가 일치하지 않습니다.");
            return "register";
        }

        if (memberService.isUsernameDuplicate(memberRequest.getUsername())) {
            model.addAttribute("error", "이미 사용 중인 아이디입니다.");
            return "register";
        }

        memberService.register(
                memberRequest.getUsername(),
                memberRequest.getPassword(),
                memberRequest.getName(),
                memberRequest.getGender(),
                memberRequest.getPhoneNumber(),
                memberRequest.getAddress(),
                memberRequest.getSsn()
        );


        return "redirect:/login";
    }
    @GetMapping("/check-username")
    @ResponseBody
    public String checkUsername(@RequestParam String username) {
        System.out.println("🔥 [중복확인 요청] username = " + username);
        boolean isDuplicate = memberService.isUsernameDuplicate(username);
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
