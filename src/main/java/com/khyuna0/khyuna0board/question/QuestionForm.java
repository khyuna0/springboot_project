package com.khyuna0.khyuna0board.question;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
@Setter
@Getter
public class QuestionForm { // 질문 글의 제목과 내용의 유효성 체크
	
	@NotEmpty(message = "제목은 필수 항목입니다.") // 제목이 공란으로 들어오면 작동
	@Size(max=200) // 제목 최대 200글자까지 허용
	@Size(min=5) // 최소 5글자 이상만 허용
	private String subject;
	
	@NotEmpty(message = "내용은 필수 항목입니다.") // 내용이 공란으로 들어오면 작동
	@Size(max=500) 
	@Size(min=5)
	private String content;
	
	
}
