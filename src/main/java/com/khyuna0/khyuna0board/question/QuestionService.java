package com.khyuna0.khyuna0board.question;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.khyuna0.khyuna0board.DataNotFoundException;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor 
@Service
public class QuestionService {
	
//	@Autowired
	private final QuestionRepository questionRepository;
	// @RequiredArgsConstructor에 의해 생성자 방식으로 주입된 questionRepository
	
	public List<Question> getList() { // 모든 질문 글 가져오기
		
		return questionRepository.findAll();
	}//
	
	public Question getQuestion(Integer id) {
		Optional<Question> optional = questionRepository.findById(id);
		
		if (optional.isPresent()) { // question 반환
			return optional.get();
		} else {
			throw new DataNotFoundException("question not found"); 
		}
		
	}//
	
}
