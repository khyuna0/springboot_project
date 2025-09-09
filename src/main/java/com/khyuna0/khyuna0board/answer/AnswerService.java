package com.khyuna0.khyuna0board.answer;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.khyuna0.khyuna0board.question.Question;
import com.khyuna0.khyuna0board.user.SiteUser;

@Service
public class AnswerService {
	
	@Autowired
	private AnswerRepository answerRepository;
	
	public void create(Question question, String content, SiteUser author) {
		Answer answer = new Answer();
		answer.setContent(content);
		answer.setCreatedate(LocalDateTime.now()); //현재 시간 등록(답변 등록 시간)
		answer.setQuestion(question);
		answer.setAuthor(author);
		
		answerRepository.save(answer);
	}
}

