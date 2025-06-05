package kr.or.ddit.conf;

import javax.sql.DataSource;

import org.apache.ibatis.annotations.Mapper;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;


@Configuration
@MapperScan(
		basePackages = "kr.or.ddit.mapper"
		, annotationClass = Mapper.class
)
public class MapperContextConfig {
	@Bean
	public SqlSessionFactoryBean sqlSessionFactory(
		@Value("classpath:kr/or/ddit/mybatis/Configuration.xml")
			Resource configLocation
		, DataSource dataSource
		, @Value("classpath:kr/or/ddit/mapper/**/*.xml")
			Resource...mapperLocations
	) {
		SqlSessionFactoryBean factoryBean =
				new SqlSessionFactoryBean();
		factoryBean.setConfigLocation(configLocation);
		factoryBean.setDataSource(dataSource);
		factoryBean.setMapperLocations(mapperLocations);
		return factoryBean;
	}
}
