package kr.or.ddit.login;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import jakarta.servlet.http.HttpSession;
import kr.or.ddit.member.service.AuthenticateService;
import kr.or.ddit.vo.MemberVO;

@Controller // 이 클래스가 Spring MVC의 컨트롤러 역할을 한다는 것을 명시
public class LoginController { 

	@Autowired // AuthenticateService 빈을 자동으로 주입하여 인증 처리를 담당
	private AuthenticateService service;

	@GetMapping("/login") // GET 요청으로 로그인 페이지를 호출하면 loginForm() 실행
	public String loginForm() { 
		return "login/loginForm"; // "login/loginForm" 뷰를 반환하여 로그인 화면을 표시
	}

	@PostMapping("/login") // 로그인 요청이 POST 방식으로 들어오면 loginCheck() 실행
	public String loginCheck(
		HttpSession session, // 사용자 정보를 저장할 세션 객체
		@RequestParam String username, // 요청 파라미터에서 사용자 ID 추출
		@RequestParam String password, // 요청 파라미터에서 비밀번호 추출
		RedirectAttributes redirectAttributes // 리다이렉트 시 메시지를 전달하기 위한 객체
	) { 
		String dest = null; // 로그인 후 이동할 목적지 변수
		String message = null; // 로그인 실패 메시지 변수

		// 4. 검증 통과
		MemberVO inputData = new MemberVO(); // MemberVO 객체 생성
		inputData.setMemId(username); // 사용자 ID 설정
		inputData.setMemPassword(password); // 비밀번호 설정

		// 1) 인증 여부 판단
		if(service.authenticate(inputData)) { // 인증 성공 여부 확인
			// 2) 인증 성공 : 웰컴 페이지 이동
			// 1. Principal 구현 객체 생성 (여기에서는 직접적으로 생성하지 않음)
			// 2. request.getUserPrincipal에서 반환될 수 있도록 세션에 설정
			// --> Web Filter 활용 가능
			session.setAttribute("authUser", inputData); // 세션에 사용자 정보 저장
			dest = "redirect:/"; // 인증 성공 시 홈페이지로 리다이렉트
		}else { 
			// 3) 인증 실패 : 로그인 페이지로 이동
			dest = "redirect:/login"; // 인증 실패 시 로그인 페이지로 리다이렉트
			message = "아이디와 비밀번호가 서로 다른 경우, 로그인 실패"; // 오류 메시지 설정
		}

		// 메시지가 비어있지 않다면 메시지를 리다이렉트 시 함께 전달
		if(StringUtils.isNotBlank(message)) { 
			redirectAttributes.addFlashAttribute("message", message);
		}

		return dest; // 최종적으로 목적지 반환
	}

	@GetMapping("/logout") // 로그아웃 요청이 GET 방식으로 들어오면 실행
	public String logout(HttpSession session) { 
		session.invalidate(); // 세션을 무효화하여 로그아웃 처리
		return "redirect:/"; // 로그아웃 후 홈페이지로 리다이렉트
	}
}
















