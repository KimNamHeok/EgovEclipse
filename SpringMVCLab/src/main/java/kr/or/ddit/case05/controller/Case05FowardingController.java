package kr.or.ddit.case05.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequestMapping("/case05")
public class Case05FowardingController {
	@RequestMapping("start01")
	public String start01(Model model) {
		model.addAttribute("modelInfo", "전달할 모델");
		return "forward:/case05/dest01";
	}
	
	@RequestMapping("dest01")
	public String dest01(@RequestAttribute String modelInfo){
		log.info("{}", modelInfo);
		return "case/view01";		
	}
}
