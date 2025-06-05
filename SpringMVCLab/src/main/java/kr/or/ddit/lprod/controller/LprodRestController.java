package kr.or.ddit.lprod.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import kr.or.ddit.lprod.service.LprodService;
import kr.or.ddit.vo.LprodVO;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class LprodRestController {
	private final LprodService service;
	
	@GetMapping("/ajax/lprod")
	public List<LprodVO> lpordList() {
		List<LprodVO> lprodList = service.readLprodList();
		return lprodList;
		
	}
}
