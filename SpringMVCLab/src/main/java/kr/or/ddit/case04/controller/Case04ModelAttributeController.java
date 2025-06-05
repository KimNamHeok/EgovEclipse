package kr.or.ddit.case04.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/case04")
public class Case04ModelAttributeController {
	
	@ModelAttribute("modelAttr")
	public String modelAttr() {
		return "MODEL INFO";
	}
	
	@GetMapping("model04")
	public String handler04() {
		return "case04/view01";
	}
	
	@GetMapping("model03")
	public String handler03() {
		return "case04/view01";
	}
}
