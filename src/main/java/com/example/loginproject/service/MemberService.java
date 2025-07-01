package com.example.loginproject.service; //service 패키지안에 있는 클래스 기능들을 모아둔곳

import com.example.loginproject.domain.Member; //엔티티 사용
import com.example.loginproject.repository.MemberRepository; //리포지토리 사용
import org.springframework.beans.factory.annotation.Autowired; //스프링이 자동으로 Autowired, encoder 넣어줌
import org.springframework.stereotype.Service; //이 클래스가 서비스 역할임을 스프링에 알려줘서 bean에 등록
import java.util.Optional;

import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;




@Service
public class MemberService implements UserDetailsService { //스프링이 로그인할때 호출하는 클래스 UserDetailService를 상속하면 로그인시 자동 호출

    @Autowired //의존성 주입
    private MemberRepository memberRepository; //db에 회원 저장 조회

    @Autowired
    private PasswordEncoder passwordEncoder; //비밀번호 암호화에 사용

    // 회원가입 (비밀번호 암호화 포함)
    public void register(String username, String rawPassword, String name, //비밀번호 암호화 후 member 객체 생성
                         String gender, String phoneNumber, String address, String ssn) {//후에 db에 저장
        String encodedPassword = passwordEncoder.encode(rawPassword);

        Member member = new Member(username, encodedPassword, name, gender, phoneNumber, address, ssn);
        memberRepository.save(member);
    }


    // Spring Security가 로그인 시 자동으로 호출함
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Member member = memberRepository.findByUsername(username) //로그인 요청시 아이디를 찾음
                .orElseThrow(() -> new UsernameNotFoundException("사용자 없음")); //db에 아이디가 없다면 에러표시

        return User.builder() //스프링시큐리티에서 제공하는 유저 객체 생성기
                .username(member.getUsername()) //로그인폼에서 입력한 id와 연결해줄 이름
                .password(member.getPassword()) //db에서 꺼낸 암호화된 비밀번호 - 스프링이 로그인때 비교해줌
                .roles("USER") //권한 설정
                .build();
    }
    public boolean isUsernameDuplicate(String username) { //아이디 중복체크
        return memberRepository.findByUsername(username).isPresent(); //Optional<Member> 형태로 조회 후 db에 값이 있으면 true 없으면 false
    }
    public Member findByUsername(String username) { //실제 로그인 한 사용자 조회 Member에서 객체 자체를 반환
        return memberRepository.findByUsername(username).orElseThrow(() -> new RuntimeException("사용자 없음"));
    }//사용자 정보가 반드시 필요할때 사용 주문,마이페이지 등 없으면 바로 예외로 종료
}
