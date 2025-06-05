package kr.or.ddit.case07.vo;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import kr.or.ddit.validate.InsertGroup;
import lombok.Data;

@Data
public class DummyVO {
	// 문자로 전달된 name(필수), 숫자로 전달된 age(옵셔널)
	// 문자로 전달된 hobbies(옵셔널)
	@NotBlank(groups = InsertGroup.class)
	private String name;
	@Min(1)
	private int age;
	private String[] hobbies;
}
