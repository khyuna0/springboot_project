package com.khyuna0.khyuna0board.question;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.khyuna0.khyuna0board.DataNotFoundException;
import com.khyuna0.khyuna0board.answer.Answer;
import com.khyuna0.khyuna0board.user.SiteUser;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import jakarta.transaction.Transactional;
import javassist.SerialVersionUID;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor 
@Service
public class QuestionService {
	
//	@Autowired
	private final QuestionRepository questionRepository;
	// @RequiredArgsConstructor에 의해 생성자 방식으로 주입된 questionRepository
	
	public List<Question> getList() { // 모든 질문 글 가져오기 -> 페이징
		//Pageable pageable = PageRequest.of(page, 10); // 1 페이지 당 10개의 게시글 표시
		return questionRepository.findAll();
	}//
	
	public Question getQuestion(Integer id) { // 기본키인 질문글 번호로 질문 1개 가져오기
		Optional<Question> optional = questionRepository.findById(id);
		
		if (optional.isPresent()) { // question 반환
			return optional.get();
		} else {
			throw new DataNotFoundException("question not found"); 
		}
		
	}//
	
	
	public void create(String subject, String content, SiteUser user) { // 질문 글 쓰기
		Question question = new Question();
		question.setSubject(subject);
		question.setContent(content);
		question.setCreatedate(LocalDateTime.now());
		question.setAuthor(user); // 글쓴이 엔티티 추가
		questionRepository.save(question);
		
	}
	
	public void modify(Question question, String subject, String content ) { // 질문 글 수정하기
		
		question.setSubject(subject); // 새로운 제목으로 저장하기
		question.setContent(content); // 새로운 내용
		question.setModifydate(LocalDateTime.now()); // 수정 일시로 업데이트
		
		questionRepository.save(question); // 엔티티 수정
	}
	
	public void delete(Question question) {
		questionRepository.delete(question);
	}
	
	public void vote(SiteUser siteUser, Question question) { // 질문 글 추천 (업데이트)
		question.getVoter().add(siteUser); 
		// question -> 추천을 받은 글의 번호로 조회한 질문 엔티티
		// question 의 멤버인 voter 를 get 해서 voter 에 추천을 누른 유저의 엔티티를 추가해줌
		questionRepository.save(question); // 추천한 유저 수가 변경된 질문 엔티티를 다시 save해서 갱신
	}
	
	public void voteN(SiteUser siteUser, Question question) { // 질문 글 비추천
		question.getVoterN().add(siteUser);
		questionRepository.save(question);
	}
	
	public void questionHit(Question question) {  // 조회수 증가
		question.setHit(question.getHit() + 1);
		questionRepository.save(question);	
	}
	
	// 페이징
	public Page<Question> getPageQuestions(int page, String kw) {
		
		//Specification<Question> spec = search(kw);
		
		int size = 10; // 1 페이지 당 글의 개수 (10개)
		int startRow = page * size; // 첫 페이지(page = 0) 시작할 레코드 넘버
		int endRow = startRow + size; // 페이지 끝나는 레코드 넘버
		
		// 한 페이지 당 출력될 글 리스트
		//List<Question> pageQuestionList = questionRepository.findQuestionsWithPaging(startRow, endRow);
		//long totlaSearchQuestion = questionRepository.count();
		
		// 검색어로
		List<Question> searchQuestionList = questionRepository.searchQuestionsWithPaging(kw, startRow, endRow);
		int totalSearchQuestion = questionRepository.countSearchResult(kw);
		
		
		long totalQuestion = questionRepository.count(); // 모든 글 개수
		
		// ( 실제 출력될 리스트 + 현재 페이지 정보 + 전체 글 개수 )를 페이지 객체로 묶어서 반환함
		//Page<Question> pagingList = new PageImpl<>(pageQuestionList, PageRequest.of(page, size), totalQuestion);
		Page<Question> pagingList = new PageImpl<>(searchQuestionList, PageRequest.of(page, size), totalSearchQuestion);
		
		return pagingList;
	}
	
	// 검색
	/*
	private Specification<Question> search(String kw) { // 키워드 검색 조회 메서드
		
			return new Specification<Question>() {		
			private static final long SerialVersionUID = 1L;
	
			@Override
			public Predicate toPredicate(Root<Question> q, CriteriaQuery<?> query, CriteriaBuilder cb) {
				query.distinct(true); // 중복 제거
				Join<Question, SiteUser> u1 = q.join("author",JoinType.LEFT); // Question - SiteUser 테이블 조인
				Join<Question, Answer> a = q.join("answerlist", JoinType.LEFT); // Question - Answer 테이블 조인
				Join<Answer, SiteUser> u2 = a.join("author", JoinType.LEFT);	// Answer - SiteUser 테이블 조인
				// CriteriaBuilder -> WHERE 조건
				return cb.or(cb.like(q.get("subject"), "%" + kw + "%"), // 질문 내용
						cb.like(q.get("content"), "%" + kw + "%"), // 질문 내용
						cb.like(u1.get("username"), "%" + kw + "%"),// 질문 작성자 검색어 조회
						cb.like(a.get("content"), "%" + kw + "%"), // 답변 내용에서 검색어 조회
						cb.like(u2.get("username"), "%" + kw + "%") // 답변 작성자에서 검색어 조회
						);
			}
		}; 
		
	}//
	*/
	
	 
}
