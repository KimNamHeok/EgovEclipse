package kr.or.ddit.member.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import kr.or.ddit.member.service.MemberService;
import kr.or.ddit.vo.MemberVO;

	

@Controller
public class MyPageController {
		
	@Autowired
	private MemberService service;
	
	@GetMapping("/mypage")
	public String mypage(
	  Authentication authentication,
	  @AuthenticationPrincipal kr.or.ddit.security.auth.MemberVOWrapper principal,
	  @AuthenticationPrincipal(expression = "realUser") MemberVO realUser,
	  Model model) {
	  String username = authentication.getName();
//	  principal.getUsername();
//	  realUser.getMemId();
	  MemberVO member = service.readMember(username);
	  model.addAttribute("member", member);
	  return "member/mypage";
	  
	}
}

