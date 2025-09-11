package com.khyuna0.khyuna0board.question;

import java.awt.print.Pageable;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


public interface QuestionRepository extends JpaRepository<Question, Integer> {
	

	// SELECT * FROM question WHERE subject = ?
	public Question findBySubject(String subject); // subject -> 테이블에 존재해야 하는 필드이름, 질문 제목으로 조회한 글의 번호가 3번인지 테스트
		
	public Question findBySubjectAndContent(String subject, String content);

	// SELECT * FROM question WHERE subject Like %?% 
	public List<Question> findBySubjectLike(String subject); 	// 제목에 특정 단어가 포함된 레코드 반환
	
	// public Page<Question> findAll(Pageable pageable);
	
	// 기본 페이징
	@Query(
	         value = " SELECT * FROM ( " +
	                 " SELECT q.*, ROWNUM rnum FROM ( " +
	                 " SELECT * FROM question ORDER BY createdate DESC " +
	                 " ) q WHERE ROWNUM <= :endRow " +
	                 ") WHERE rnum > :startRow",
	         nativeQuery = true)
	    List<Question> findQuestionsWithPaging(@Param("startRow") int startRow,
	                                           @Param("endRow") int endRow);	
	// 검색 결과 조회 + 페이징
	@Query(value = 
		       "SELECT * FROM ( " +
		       "   SELECT q.*, ROWNUM rnum FROM ( " +
		       "       SELECT DISTINCT q.* " +
		       "       FROM question q " +
		       "       LEFT OUTER JOIN siteuser u1 ON q.author_id = u1.id " +
		       "       LEFT OUTER JOIN answer a ON a.question_id = q.id " +
		       "       LEFT OUTER JOIN siteuser u2 ON a.author_id = u2.id " +
		       "       WHERE q.subject LIKE '%'|| :kw || '%' " +
		       "          OR q.content LIKE '%'|| :kw || '%' " +
		       "          OR u1.username LIKE '%'|| :kw || '%' " +
		       "          OR a.content LIKE '%'|| :kw || '%' " +
		       "          OR u2.username LIKE '%'|| :kw || '%' " +
		       "       ORDER BY q.createdate DESC " +
		       "   ) q WHERE ROWNUM <= :endRow " +
		       ") WHERE rnum > :startRow", 
		       nativeQuery = true)
	    List<Question> searchQuestionsWithPaging(@Param("kw")String kw, @Param("startRow") int startRow,
	                                           @Param("endRow") int endRow);	
	
	// 검색 결과 총 개수 반환
	@Query(value = 
		       " SELECT COUNT(DISTINCT q.id) " +
		       "       FROM question q " +
		       "       LEFT OUTER JOIN siteuser u1 ON q.author_id = u1.id " +
		       "       LEFT OUTER JOIN answer a ON a.question_id = q.id " +
		       "       LEFT OUTER JOIN siteuser u2 ON a.author_id = u2.id " +
		       "       WHERE q.subject LIKE '%'|| :kw || '%' " +
		       "          OR q.content LIKE '%'|| :kw || '%' " +
		       "          OR u1.username LIKE '%'|| :kw || '%' " +
		       "          OR a.content LIKE '%'|| :kw || '%' " +
		       "          OR u2.username LIKE '%'|| :kw || '%' ", 
		       nativeQuery = true)
	  public int countSearchResult(@Param("kw")String kw);
}

