# 🛒 쇼핑몰 로그인/회원가입 프로젝트

Spring Boot 기반으로 로그인 및 회원가입 기능을 구현한 미니 프로젝트입니다.  
포트폴리오용으로 제작되었으며, 향후 쇼핑몰 기능까지 확장할 예정입니다.

---

## ✅ 사용 기술

- Java 21
- Spring Boot 3.5.0
- Spring Security
- Spring Data JPA (Hibernate)
- MySQL
- Thymeleaf
- Lombok
- Git / GitHub

---

## 📁 프로젝트 구조

src/
├── main/
│ ├── java/
│ │ └── com.example.loginproject/
│ │ ├── config/ # Spring Security 설정
│ │ ├── controller/ # MemberController, MainController 등
│ │ ├── domain/ # Member 엔티티
│ │ ├── repository/ # MemberRepository
│ │ └── service/ # MemberService
│ └── resources/
│ ├── templates/ # Thymeleaf HTML (login.html, register.html 등)
│ └── application.properties

---

## 💻 실행 방법

1. `application.properties`에 DB 접속 정보 입력
2. `./mvnw spring-boot:run` 또는 IntelliJ 실행
3. 브라우저에서 `http://localhost:8080` 접속

---

## ✨ 구현 기능

- [x] 회원가입 기능
    - 아이디 중복 확인
    - 비밀번호 확인 (일치 검사)
    - 유효성 검사 (모든 필드 필수 입력)
- [x] 로그인 기능
    - 로그인 실패 시 에러 메시지 출력
    - 로그인 성공 시 `/main` 페이지로 이동
- [x] 로그아웃 기능
- [x] Thymeleaf 기반 HTML 폼 구성
- [x] GitHub에 프로젝트 연동

---

## 🔧 구현 예정 기능

- [ ] 회원정보 수정 / 탈퇴
- [ ] 관리자 페이지 및 권한 분리
- [ ] 이용자 게시판 기능 구현
- [ ] 상품 등록 / 목록 / 상세 보기
- [ ] 장바구니 기능
- [ ] 주문 및 결제 흐름 구현
- [ ] 프론트엔드 개선 (Bootstrap 또는 jQuery)

---

## 📌 참고

- 비밀번호는 `BCryptPasswordEncoder`로 암호화 저장됨
- Spring Security의 `UserDetailsService`를 직접 구현하여 사용자 인증 처리
- GitHub Personal Access Token(PAT)을 통해 원격 push 설정 완료

---

## ✍️ 개발자

- **강민제**
- 공부용 개인 프로젝트
- GitHub: [https://github.com/minchey]
