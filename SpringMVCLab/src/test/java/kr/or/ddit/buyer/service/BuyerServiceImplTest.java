package kr.or.ddit.buyer.service;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import kr.or.ddit.annotation.RootContextConfig;

@RootContextConfig
class BuyerServiceImplTest {
	@Autowired
	BuyerService service;
	
	@Test
	void testReadBuyer() {
		fail("Not yet implemented");
	}

	@Test
	void testReadBuyerList() {
		service.readBuyerList();
	}

	@Test
	void testCreateBuyer() {
		fail("Not yet implemented");
	}

	@Test
	void testModifyBuyer() {
		fail("Not yet implemented");
	}

}
