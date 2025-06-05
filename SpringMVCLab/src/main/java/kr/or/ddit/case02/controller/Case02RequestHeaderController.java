package kr.or.ddit.case02.controller;

import java.util.Map;

import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Controller;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;

/**
 * 요청 헤더를 수신하고, 그걸 파싱하고, 헨들러 메소드를 실행하는 건???
 * HandlerAdapter 에 의해 처리 됨.
 * 요청 헤더 수신 : @RequestHeader(name, requried, defaultValue)
 * 파싱?? : 헨들러 메소드 아규먼트 타입으로 파싱 타입이 결정됨.
 * ex) @RequestHeader(name="myAge", required=false, defaultValue="34") int myAge
 * 
 * HandlerAdapter
 * 	: HandlerMapping 핸들러를 검색하면, HandlerAdapter 가 POJO 기반의 핸들러를 실행하는 역할을 함
 * 
 */

@Slf4j
@Controller
@RequestMapping("/case02")
public class Case02RequestHeaderController {
	@GetMapping("header07")
	public void handler07(
		@CookieValue(value = "dummyCookie", required = false, defaultValue = "DEFAULTVALUE") String dummyCookie) {
		log.info("case02/header07 수신, dummyCookie : {}", dummyCookie);
	}
	@GetMapping("header06")
	public void handler06(@RequestHeader String cookie) {
		log.info("case02/header06 수신, Cookie : {}", cookie);
	}
	@GetMapping("header05")
	public void handler05(@RequestHeader HttpHeaders headers) {
		log.info("case02/header05 수신, headers : {}", headers);
	}
	@GetMapping("header04")
	public void handler04(@RequestHeader MultiValueMap<String, String> headers) {
		log.info("case02/header04 수신, headers : {}", headers);
	}
	
	@GetMapping("header03")
	public void handler03(@RequestHeader("accept-language") String acceptLanguage) {
		log.info("case02/header03 수신, acceptLanguage header : {}", acceptLanguage);
	}
	
	@GetMapping("header02")
	public void handler02(@RequestHeader(required = false, defaultValue = "*/*") String accept) {
		log.info("case02/header02 수신, accept header : {}", accept);
	}
	
	@GetMapping("header01")
	public void handler01(HttpServletRequest req) {
		log.info("case02/header01 수신, accept header : {}", req.getHeader("accept"));
	}
}








