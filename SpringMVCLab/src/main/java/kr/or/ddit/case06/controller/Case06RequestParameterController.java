package kr.or.ddit.case06.controller;

import java.util.Arrays;
import java.util.Map;
import java.util.Optional;

import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.server.ResponseStatusException;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequestMapping("/case06")
public class Case06RequestParameterController {
	// 문자로 전달된 name(필수), 숫자로 전달된 age(옵셔널)
	// 문자들로 전달된 hobbies(옵셔널)
	
	@RequestMapping("parameter03")
	public String hander02(
	  @RequestParam MultiValueMap<String, String> parameters
	  , Model model
	) {
		log.info("parameters : {}", parameters);
		model.addAllAttributes(parameters);
		return "case06/view01";
	}
	@GetMapping("parameter02")
	public String hander02(
	  @RequestParam String name
	  , @RequestParam(required = false, defaultValue = "-1") int age
	  , @RequestParam(required = false) String[] hobbies
	  , Model model
	) {
		log.info("name : {}", name);
		log.info("age : {}", age);
		log.info("hobbies : {}", Arrays.toString(hobbies));
		model.addAttribute("name", name);
		model.addAttribute("age", age);
		model.addAttribute("hobbies", hobbies);
		return "case06/view01";
	}
	@GetMapping("parameter01")
	public String hander01(HttpServletRequest req) {
		String name = req.getParameter("name");
		if(StringUtils.isBlank(name)) {
			throw new ResponseStatusException(HttpStatusCode.valueOf(400));
		}
		int age = Optional.ofNullable(req.getParameter("age"))
				.map(Integer::parseInt)
				.orElse(-1);
		String[] hobbies = req.getParameterValues("hobbies");
		log.info("name : {}", name);
		log.info("age : {}", age);
		log.info("hobbies : {}", Arrays.toString(hobbies));
		return "case06/view01";
	}
}
