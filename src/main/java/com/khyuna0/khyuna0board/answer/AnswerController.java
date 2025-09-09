package com.khyuna0.khyuna0board.answer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;


import com.khyuna0.khyuna0board.question.Question;
import com.khyuna0.khyuna0board.question.QuestionService;

import jakarta.validation.Valid;
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
	public String createAnswer(Model model, @PathVariable("id") Integer id, @Valid AnswerForm answerForm,  BindingResult bindingResult  ) {
		Question question = questionService.getQuestion(id);	
		
		if(bindingResult.hasErrors()) {
			model.addAttribute("question", question); 
			return "question_detail";
		}  
		answerService.create(question,answerForm.getContent()); //DB에 답변 등록
		
		return String.format("redirect:/question/detail/%s", id);  
	}
}
