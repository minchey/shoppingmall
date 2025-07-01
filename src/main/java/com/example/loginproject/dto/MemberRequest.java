package com.example.loginproject.dto; //폼에서 입력한 데이터 전달 하기 위한 객체

import jakarta.validation.constraints.NotBlank; //필드가 null이거나 빈문자열이면 유효하지 않다는 유효성 검사 어노테이션
import lombok.Getter; //lombok을 통해 getter 자동생성
import lombok.Setter; //lombok을 통해 setter 자동생성

@Getter
@Setter
public class MemberRequest { //회원가입 폼 데이터 담는 용도 @ModerAttribute로 이 DTO 자동으로 바인딩


    //빈칸인지 확인 하는 코드 빈칸일시 에러메시지 출력
    @NotBlank(message = "아이디는 필수입니다.")
    private String username;

    @NotBlank(message = "비밀번호는 필수입니다.")
    private String password;

    @NotBlank(message = "비밀번호 확인은 필수입니다.")
    private String confirmPassword;

    @NotBlank(message = "이름은 필수입니다.")
    private String name;

    @NotBlank(message = "성별은 필수입니다.")
    private String gender;

    @NotBlank(message = "전화번호는 필수입니다.")
    private String phoneNumber;

    @NotBlank(message = "주소는 필수입니다.")
    private String address;

    @NotBlank(message = "주민등록번호는 필수입니다.")
    private String ssn;
}
