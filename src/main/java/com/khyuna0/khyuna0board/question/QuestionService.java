package com.khyuna0.khyuna0board.question;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.khyuna0.khyuna0board.DataNotFoundException;

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
	
	
	public void create(String subject, String content) { // 질문 글 쓰기
		Question question = new Question();
		question.setSubject(subject);
		question.setContent(content);
		question.setCreatedate(LocalDateTime.now());
		questionRepository.save(question);
		
	}
	
}
