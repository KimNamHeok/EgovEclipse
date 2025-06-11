package kr.or.ddit.login;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller // 이 클래스가 Spring MVC의 컨트롤러 역할을 한다는 것을 명시
public class LoginController {

	@GetMapping("/login") // GET 요청으로 로그인 페이지를 호출하면 loginForm() 실행
	public String loginForm() {
		return "login/loginForm"; // "login/loginForm" 뷰를 반환하여 로그인 화면을 표시
	}

	@GetMapping("/logout") // 로그아웃 요청이 GET 방식으로 들어오면 실행
	public String logout() {
		return "login/logout"; // 로그아웃 후 홈페이지로 리다이렉트
	}
}
