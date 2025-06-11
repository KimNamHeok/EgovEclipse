package kr.or.ddit.conf;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PropertiesLoaderUtils;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;

import kr.or.ddit.filter.auth.AuthenticationCheckFilter;
import kr.or.ddit.filter.auth.GeneratePrincipalFilter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Configuration
public class SpringSecurityConfig {
	@Bean
	public PasswordEncoder passwordEncoder() {
		return PasswordEncoderFactories.createDelegatingPasswordEncoder();
	}
	
	
	
	@Bean
	public Map<String, List<String>> securedResources(
		@Value("classpath:kr/or/ddit/SecuredResources.properties") Resource securedResourcesFile
	) throws IOException{
		Map<String, List<String>> securedMap = new HashMap<>();
		Properties securedProps = PropertiesLoaderUtils.loadProperties(securedResourcesFile);
		for(String url : securedProps.stringPropertyNames()) {
			String roles = securedProps.getProperty(url);
			securedMap.put(url,Arrays.asList(roles.split(",")));
			log.info("보호자원 추가 : {} - {}", url, roles);
		}
		return securedMap;
	}
	
	@Bean
	public FilterRegistrationBean<GeneratePrincipalFilter> principalFilter(){
		FilterRegistrationBean<GeneratePrincipalFilter> filter = 
											new FilterRegistrationBean<>();
		filter.setFilter(new GeneratePrincipalFilter());
		filter.addUrlPatterns("/*");
		filter.setOrder(100);
		return filter;
	}
	
	@Bean
	public FilterRegistrationBean<AuthenticationCheckFilter> authenticationFilter(
		Map<String,List<String>> securedResources
	){
		FilterRegistrationBean<AuthenticationCheckFilter> filter =
										new FilterRegistrationBean<>();
		filter.setFilter(new AuthenticationCheckFilter(securedResources));
		filter.setOrder(101);
		filter.addUrlPatterns("/*");
		return filter;
	}
	
	@Bean
	public FilterRegistrationBean<AuthenticationCheckFilter> authorizationFilter(
		Map<String,List<String>> securedResources
	){
		FilterRegistrationBean<AuthenticationCheckFilter> filter =
				new FilterRegistrationBean<>();
		filter.setFilter(new AuthenticationCheckFilter(securedResources));
		filter.setOrder(102);
		filter.addUrlPatterns("/*");
		return filter;
	}
}












