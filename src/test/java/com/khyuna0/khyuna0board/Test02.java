package com.khyuna0.khyuna0board;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import com.khyuna0.khyuna0board.anwser.Answer;
import com.khyuna0.khyuna0board.anwser.AnswerRepository;
import com.khyuna0.khyuna0board.question.Question;
import com.khyuna0.khyuna0board.question.QuestionRepository;

@SpringBootTest
public class Test02 {
	
	@Autowired
	private QuestionRepository questionRepository;
	
	@Autowired
	private AnswerRepository answerRepository;
	
//	@Test
//	@DisplayName("질문 게시판 제목 수정하기")
//	public void testJpa1() {
//		// 기본 키 조회 후 Setter로 변경, 다시 수정
//		Optional<Question> optional = questionRepository.findById(3);
//		assertTrue(optional.isPresent()); // 기본키로 가져온 레코드가 존재하면 T -> 테스트 통과
//		// 기본키로 가져온 레코드가 존재하지 않으면 F (오류 발생) , 테스트 종료(테스트 실패)
//		Question question = optional.get();		
//		question.setSubject("수정된 제목");
//		
//		this.questionRepository.save(question);
//	}
	
//	@Test
//	@DisplayName("질문 게시판 글 삭제")
//	public void testJpa2() {
//		assertEquals(2, questionRepository.count()); // questionRepository.count() -> 모든 레코드의 개수 반환
//		Optional<Question> op = questionRepository.findById(3);
//		assertTrue(op.isPresent());
//		Question q = op.get();
//		questionRepository.delete(q); // delete(엔티티)
//		assertEquals(1, questionRepository.count());
//		
//	}

//	@Test
//	@DisplayName("답변 게시판 글 조회")
//	public void testJpa4() {
//		Optional<Answer> op = answerRepository.findById(1);
//		assertTrue(op.isPresent());
//		Answer a = op.get();
//		
//		assertEquals(4, a.getQuestion().getId()); // 부모 질문글의 번호로 확인 테스트
//		 
//		
//	}
	
	@Test
	@DisplayName("질문 글 들 통해 답변 글 찾기")
	@Transactional
	
	public void testJpa5() {
		// 질문 글 가져오기
		Optional<Question> op = questionRepository.findById(4);
		assertTrue(op.isPresent());
		Question q = op.get();;
		
		List<Answer> answerList = q.getAnswerList();
		// 게으른 초기화 문제 오류 -> 이미  q 엔티티가 닫힌 후에 초기화 시도함
		// 테스트 과정에서만 발생하는 에러 -> @Transactional 어노테이션으로 방어 가능
		
		assertEquals(1, answerList.size());
		assertEquals("네 자동으로 생성됩니다." , answerList.get(0).getContent());		
		
	}

}
