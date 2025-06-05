package kr.or.ddit.case10.vo;

import org.springframework.web.multipart.MultipartFile;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import kr.or.ddit.validate.constraints.FileNotEmpty;
import kr.or.ddit.validate.constraints.MimeTypeCheck;
import lombok.Data;
@Data
public class DummyFileVO {
	@NotBlank
	private String uploader;
	@MimeTypeCheck(mainType="image")
	@FileNotEmpty()
	private MultipartFile uploadFile;
	
}
