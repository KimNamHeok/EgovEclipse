package kr.or.ddit.mapper;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import kr.or.ddit.annotation.RootContextConfig;
import kr.or.ddit.vo.BuyerVO;
import lombok.extern.slf4j.Slf4j;
@Slf4j
@RootContextConfig
class BuyerMapperTest {

	@Autowired
	BuyerMapper mapper;
	
	@Test
	void testInsert() {
		BuyerVO buyer = new BuyerVO();
		buyer.setBuyerName("신규제조사");
		buyer.setLprodGu("P101");
		buyer.setBuyerBank("하나");
		buyer.setBuyerBankname("계좌주");
		buyer.setBuyerBankno("0000");
		buyer.setBuyerComtel("000-000-0000");
		buyer.setBuyerMail("aa@naver.com");
		buyer.setBuyerCharger("담당");
		assertEquals(1, mapper.insertBuyer(buyer));
	}
	
	@Test
	void testSelectBuyerList() {
		mapper.selectBuyerList()
			.forEach((b)->log.info("{}, {}", b.getBuyerName(), b.getProdList().size()));
	}
	
	@Test
	void testSelectBuyer() {
		// P10101 을 조회하고,
		// 제조사명과 제조사주소, 제조수 분류명, 거래품목명, 구매가 를 로그로 출력.
		BuyerVO buyer = mapper.selectBuyer("P10120");
		log.info("제조사 정보 : {}, {}, {}",
			buyer.getBuyerName(), buyer.getBuyerAdd2()
			, buyer.getLprod().getLprodName()
		);
		buyer.getProdList()
			.forEach((prod)->log.info("거래품목 : {}, {}, {}"
					, prod.getProdName(), prod.getProdCost(), prod.getBuyerId()));
	}
	

}