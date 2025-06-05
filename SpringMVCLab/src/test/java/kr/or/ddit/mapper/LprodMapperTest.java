package kr.or.ddit.mapper;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import jakarta.annotation.PostConstruct;
import kr.or.ddit.annotation.RootContextConfig;
import kr.or.ddit.conf.SpringRootContextConfig;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RootContextConfig
class LprodMapperTest {

	@Autowired
	LprodMapper mapperProxy;
	
	@PostConstruct
	private void init() {
		log.info("mapper proxy : {}", mapperProxy);
	}
	
	@Test
	void testSelectLprodList() {
		mapperProxy.selectLprodList();
	}

}
