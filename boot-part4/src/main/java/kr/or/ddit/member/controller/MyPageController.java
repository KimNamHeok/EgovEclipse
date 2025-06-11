package kr.or.ddit.member.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import kr.or.ddit.filter.auth.MemberVOWrapper;
import kr.or.ddit.member.service.MemberService;
import kr.or.ddit.vo.MemberVO;

	
	
	@Controller
	public class MyPageController {
		
	@Autowired
	private MemberService service;
	
	@GetMapping("/mypage")
	public String mypage(MemberVOWrapper principal, Model model) {
	  String username = principal.getName();
	  MemberVO member = service.readMember(username);
	  model.addAttribute("member", member);
	  return "member/mypage";
	  
	}
}

