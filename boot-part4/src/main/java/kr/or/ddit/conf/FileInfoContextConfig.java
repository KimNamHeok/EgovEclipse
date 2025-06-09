package kr.or.ddit.conf;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.io.Resource;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@PropertySource("classpath:kr/or/ddit/FileInfo.properties")
public class FileInfoContextConfig implements WebMvcConfigurer {
	@Value("${imagesFolder}")
	private Resource imagesLocation;
	@Value("${imagesUrl}")
	private String imagesUrl;
	
	/**
	 * 파일시스템 자원을 웹자원으로 매핑할때 --> proDetail.jsp에서 img 태그사용에 필요한 url 구조
	 */
	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler(imagesUrl+"/**")
				.addResourceLocations(imagesLocation);     
	}
}
