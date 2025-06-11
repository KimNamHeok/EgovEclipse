package kr.or.ddit.buyer.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import kr.or.ddit.buyer.service.BuyerService;
import kr.or.ddit.vo.BuyerVO;

@RestController
@RequestMapping("/rest/buyer")
public class BuyerRestController {
	@Autowired
	private BuyerService service;
	
	@GetMapping
	public List<BuyerVO> list() {
		return service.readBuyerList();
	}
	@GetMapping("{what}")
	public BuyerVO detail(@PathVariable String what) {
		return service.readBuyer(what).get();
	}
}
