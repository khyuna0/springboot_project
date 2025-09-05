package com.khyuna0.khyuna0board;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.khyuna0.khyuna0board.entity.Question;
import com.khyuna0.khyuna0board.repository.QuestionRepository;

@SpringBootTest
public class Test01 {
	
	@Autowired
	private QuestionRepository questionRepository;
	
//	@Test
//	@DisplayName("question 테이블에 질문 글 저장하기")
//	public void testJpa1() {
//		
//		Question q1 = new Question();
//		q1.setSubject("SBB가 무엇인가요?"); // 질문 제목
//		q1.setContent("SBB에 대해 알고 싶습니다."); // 질문 내용
//		q1.setCreatedate(LocalDateTime.now()); // 현재 시간을 자바로
//		// q1 -> entity 생성 완료
//		
//		questionRepository.save(q1);
//		
//		Question q2 = new Question();
//		q2.setSubject("스프링부트 모델이 무엇인가요?"); // 질문 제목
//		q2.setContent("id는 자동 생성되나요?"); // 질문 내용
//		q2.setCreatedate(LocalDateTime.now()); // 현재 시간을 자바로
//		// q2 -> entity 생성 완료
//		
//		questionRepository.save(q2);
//	}
	
	@Test
	@DisplayName("모든 질문 글 조회하기")
	public void testJpa2() {
		List<Question> allqQuestions = questionRepository.findAll(); // 모든 질문 글 조회
		assertEquals(2, allqQuestions.size()); // 예상 결과 ( 기댓값 , 실제값 ) 기댓값 = 실제값이 아니면 테스트 실패(오류)
		
		Question question = allqQuestions.get(0); // 첫 번째 질문 글 가져오기
		assertEquals("SBB가 무엇인가요?", question.getSubject());	
	}

	@Test
	@DisplayName("id(기본키)로 조회 테스트")
	public void testJpa3() {
		Optional<Question> optional = questionRepository.findById(3); // 기본키 조회
		
		if(optional.isPresent()) { // 참이면 기본키 번호가 존재함
			Question question = optional.get();
			assertEquals("SBB가 무엇인가요?", question.getSubject());
		} else {
			System.out.println("글없음");
		}
	}
}
