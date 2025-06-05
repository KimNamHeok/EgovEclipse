package kr.or.ddit.conf;

import static org.junit.jupiter.api.Assertions.*;

import javax.sql.DataSource;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import jakarta.inject.Inject;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@SpringJUnitConfig(classes = DataSourceContextConfig.class)
class DataSourceContextConfigTest {

	@Inject
	DataSource dataSource;
	
	@Autowired
	JdbcTemplate jdbcTemplate;
	
	@Test
	void testDataSource() {
		log.info("dataSource : {}", dataSource);
	}

}
