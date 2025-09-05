package com.khyuna0.khyuna0board.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.khyuna0.khyuna0board.entity.Question;
import java.util.List;


public interface QuestionRepository extends JpaRepository<Question, Integer> {
	
	//질문 제목으로 조회한 글의 번호가 3번인지 테스트
	public Question findBySubject(String subject);
}
