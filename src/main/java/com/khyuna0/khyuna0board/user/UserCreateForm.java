package com.khyuna0.khyuna0board.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserCreateForm {
	
	@Size(min = 3, max = 25, message = "사용자 ID의 길이는 3자 이상 25자 이하입니다.") // 아이디의 길이 3 ~ 25 제한
	@NotEmpty(message = "사용자 ID는 필수 항목입니다.")
	private String username;
	
	@NotEmpty(message = "사용자 비밀번호는 필수 항목입니다.")
	private String password1;
	
	@NotEmpty(message = "사용자 비밀번호 확인은 필수 항목입니다.")
	private String password2;
	
	@NotEmpty(message = "이메일은 필수 항목입니다.")
	@Email // 이메일 형식이 아니면 에러
	private String email;
}
