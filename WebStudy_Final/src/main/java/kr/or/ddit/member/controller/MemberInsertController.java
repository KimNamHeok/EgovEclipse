package kr.or.ddit.member.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import kr.or.ddit.common.exception.PKDuplicatedException;
import kr.or.ddit.member.service.MemberService;
import kr.or.ddit.validate.InsertGroup;
import kr.or.ddit.vo.MemberVO;

@Controller
@RequestMapping("/member/memberInsert.do")
public class MemberInsertController {
	@Autowired
	private MemberService service;
	
	private static final String MODELNAME = "member";
	
	@ModelAttribute(MODELNAME)
	public MemberVO member() {
		return new MemberVO();
	}
	
	@GetMapping
	public String formUi() {
		return "member/memberForm";
	}
	
	// 이거 확실히
	@PostMapping
	public String formProcess(
		@Validated(InsertGroup.class) @ModelAttribute(MODELNAME) MemberVO member
		, BindingResult errors
		, RedirectAttributes redirectAttributes
	) {
		String lvn;
		if(!errors.hasErrors()) {
			try {
				service.createMember(member);
				lvn = "redirect:/";
			} catch (PKDuplicatedException e) {
				redirectAttributes.addFlashAttribute(MODELNAME, member);
				redirectAttributes.addFlashAttribute("message", "아이디 중복");
				// 아이디 중복
				lvn = "redirect:/member/memberInsert.do";
			}
		}else {
			redirectAttributes.addFlashAttribute(MODELNAME, member);
			String errorName = BindingResult.MODEL_KEY_PREFIX+MODELNAME;
			redirectAttributes.addFlashAttribute(errorName, errors);
			lvn = "redirect:/member/memberInsert.do";
		}
		return lvn;
	}
	
}
