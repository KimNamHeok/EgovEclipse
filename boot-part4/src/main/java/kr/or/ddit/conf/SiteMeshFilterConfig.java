package kr.or.ddit.conf;

import org.sitemesh.config.ConfigurableSiteMeshFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;

import kr.or.ddit.filter.DummyFilter;

@Configuration
public class SiteMeshFilterConfig {
	
	@Bean
	FilterRegistrationBean<DummyFilter> dummyFilter(){
		FilterRegistrationBean<DummyFilter> filter = new FilterRegistrationBean<>();
		filter.setFilter(new DummyFilter());
		// 몇번째로 동작해야하는 필터인지 설정
		filter.setOrder(Ordered.HIGHEST_PRECEDENCE+50);
		// 필터 매핑
		filter.addUrlPatterns("/*");
		return filter;
	}

	@Bean
	FilterRegistrationBean<ConfigurableSiteMeshFilter> siteMeshFilter(){
		FilterRegistrationBean<ConfigurableSiteMeshFilter> filter =
											new FilterRegistrationBean<>();
		// 실제 필터 생성
		filter.setFilter(
			ConfigurableSiteMeshFilter.create(builder->
				builder.setDecoratorPrefix("/WEB-INF/decorators/")
					.addExcludedPath("/ajax/**")
					.addExcludedPath("/rest/**")
					// 확장자 패턴 사용 (html로 나가는건 배제?)
					.addExcludedPath("*.html")
					.addDecoratorPath("/**", "mantisDecorator.jsp")
			)
		);
		// 우선 순위 결정
		filter.setOrder(Ordered.LOWEST_PRECEDENCE-100);
		// 필터 매핑 설정
		filter.addUrlPatterns("/*");
		return filter;
	}
}
