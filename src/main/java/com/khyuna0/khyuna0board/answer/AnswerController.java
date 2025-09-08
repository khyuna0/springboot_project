package com.khyuna0.khyuna0board.answer;

import org.glassfish.jaxb.core.v2.TODO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.khyuna0.khyuna0board.question.Question;
import com.khyuna0.khyuna0board.question.QuestionService;
@RequestMapping("/answer")
@Controller
public class AnswerController {

    private final AnswerService answerService;

	@Autowired
	private QuestionService questionService;

    AnswerController(AnswerService answerService) {
        this.answerService = answerService;
    }
	
    @PostMapping(value = "/create/{id}") //답변 등록 요청 -> 오는 파라미터 값 : 부모 질문글의 번호
	public String createAnswer(Model model, @PathVariable("id") Integer id, @RequestParam("content") String content) {
		Question question = questionService.getQuestion(id);		
		answerService.create(question, content); //DB에 답변 등록
		
		return String.format("redirect:/question/detail/%s", id);  
	}
}
