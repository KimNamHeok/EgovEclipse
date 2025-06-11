package kr.or.ddit.prod.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.server.ResponseStatusException;

import kr.or.ddit.prod.service.ProdService;
import kr.or.ddit.vo.ProdVO;

/**
 * 1. 요청 수신
 * 2. 요청 검증
 * 3. 로직을 사용하고, 모델을 생성
 * 4. 모델을 뷰로 전달(scope 를 통해 공유함)
 * 	- 모델의 사용 범위
 *  - 뷰로 이동하는 방식
 * 5. 뷰를 선택
 * 6. 뷰로 이동(forward, redirect)
 * 
 */

@Controller
@RequestMapping("/prod")
public class ProdReadController {
	@Autowired
	private ProdService service;
	
	
	@GetMapping("prodList.do")
	public String listHandler(Model model) {
		List<ProdVO> prodList = service.readProdList();
		
		model.addAttribute("prodList", prodList);
		
		return "prod/prodList";
	}
	
	@GetMapping("prodDetail.do")
	protected String detail(@RequestParam String what, Model model) {
		

			ProdVO prod = service.readProd(what)
								.orElseThrow(()->
									new ResponseStatusException(HttpStatusCode.valueOf(400), "그런 상품 없음"));
			model.addAttribute("prod", prod);
			return "prod/prodDetail";

		}
}












