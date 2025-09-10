package com.khyuna0.khyuna0board.answer;

import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.khyuna0.khyuna0board.DataNotFoundException;
import com.khyuna0.khyuna0board.question.Question;
import com.khyuna0.khyuna0board.question.QuestionRepository;
import com.khyuna0.khyuna0board.user.SiteUser;

@Service
public class AnswerService {

    private final QuestionRepository questionRepository;
	
	@Autowired
	private AnswerRepository answerRepository;

    AnswerService(QuestionRepository questionRepository) {
        this.questionRepository = questionRepository;
    }
	
	public void create(Question question, String content, SiteUser author) {
		Answer answer = new Answer();
		answer.setContent(content);
		answer.setCreatedate(LocalDateTime.now()); //현재 시간 등록(답변 등록 시간)
		answer.setQuestion(question);
		answer.setAuthor(author);
		
		answerRepository.save(answer);
	}
	
	public Answer getAnswer(Integer id) { // 기본키인 답변 id를 인수로 넣어주면 해당 엔티티 반환
		Optional<Answer> _answer = answerRepository.findById(id);
		
		if(_answer.isPresent()) {
			return _answer.get(); // 해당 answer 엔티티가 반환됨
		} else {
			throw new DataNotFoundException("해당 답변이 존재하지 않습니다.");
		}
		
	}
	
	public void answerModify(Answer answer, String content) { // 답변 수정하기
		answer.setContent(content);
		answer.setModifydate(LocalDateTime.now()); // 수정 일시
		answerRepository.save(answer); 
	}
	
	public void answerDelete(Answer answer) {
		answerRepository.delete(answer);
	}
	
	public void answerVote(Answer answer, SiteUser siteUser) {
		answer.getVoter().add(siteUser);
		answerRepository.save(answer);
		
	}
	
	public void voteN(SiteUser siteUser, Answer answer) { // 질문 글 비추천
		answer.getVoterN().add(siteUser);
		answerRepository.save(answer);
	}
}

