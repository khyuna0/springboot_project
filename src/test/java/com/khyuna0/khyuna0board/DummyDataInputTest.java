package com.khyuna0.khyuna0board;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.khyuna0.khyuna0board.question.QuestionService;

@SpringBootTest
public class DummyDataInputTest {
	
	@Autowired
	private QuestionService questionService;
	
	@Test
	public void testDummy() {
		
		for(int i=1; i<=300; i++) {
			String subject = String.format("테스트 데이터입니다:[%03d]", i );
			String content = "연습 내용 더미데이터입니다.";
			questionService.create(subject,content,null);	
		}
		
	}
}
