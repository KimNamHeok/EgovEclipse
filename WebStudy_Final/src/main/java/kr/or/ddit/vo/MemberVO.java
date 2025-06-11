package kr.or.ddit.vo;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Data 
@EqualsAndHashCode(of = "memId") // 데이터베이스 설계 참고
//@Getter
//@Setter
//@EqualsAndHashCode
//@ToString
public class MemberVO implements Serializable{
	@NotBlank
	private String memId;
	@NotBlank
	private String memPassword;
	@NotBlank
	private String memName;
	@ToString.Exclude
	private transient String memRegno1;
	@ToString.Exclude
	private transient String memRegno2;
	private LocalDateTime memBir;
	private String memZip;
	private String memAdd1;
	private String memAdd2;
	private String memHometel;
	private String memComtel;
	private String memHp;
	private String memMail;
	private String memJob;
	private String memHobby;
	private String memMemorial;
	private LocalDate memMemorialday;
	private Integer memMileage;
	// true : T|t|Y|y|1
	// false : F|f|N|n|0 null
	private boolean memDelete;
	
	private String memRole;
}
