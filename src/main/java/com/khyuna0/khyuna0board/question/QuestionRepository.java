package com.khyuna0.khyuna0board.question;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;


public interface QuestionRepository extends JpaRepository<Question, Integer> {
	

	// SELECT * FROM question WHERE subject = ?
	public Question findBySubject(String subject); // subject -> 테이블에 존재해야 하는 필드이름, 질문 제목으로 조회한 글의 번호가 3번인지 테스트
		
	public Question findBySubjectAndContent(String subject, String content);

	// SELECT * FROM question WHERE subject Like %?% 
	public List<Question> findBySubjectLike(String subject); 	// 제목에 특정 단어가 포함된 레코드 반환
	
	// 페이징 관련
	// public Page<Question> findAll(Pageable pageable);
	
}
