package com.khyuna0.khyuna0board.question;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class QuestionController {
	
	@Autowired
	private QuestionRepository questionRepository;
	
	@GetMapping(value = "/") // root 요청 처리
	public String root(Model model) {
		
		return "redirect:/question/list";
	}
	
	@GetMapping(value = "/question/list") // value 생략 가능
	//@ResponseBody
	public String list(Model model) {
		List<Question> questionlist = questionRepository.findAll();		
		model.addAttribute("questionList", questionlist);
		return "question_list";
	}

}