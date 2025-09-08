package com.khyuna0.khyuna0board.question;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/question")
@Controller
public class QuestionController {
	
//	@Autowired
//	private QuestionRepository questionRepository;
	
	@Autowired
	private QuestionService questionService;
	
	@GetMapping(value = "/") // root 요청 처리
	public String root(Model model) {
		
		return "redirect:/question/list";
	}
	
	@GetMapping(value = "/list") // value 생략 가능
	//@ResponseBody
	public String list(Model model) {
		//List<Question> questionlist = questionRepository.findAll();		
		List<Question> questionlist = questionService.getList();
		model.addAttribute("questionList", questionlist);
		return "question_list";
	}
	
	@GetMapping(value = "/detail/{id}") // 파라미터 이름 없이 값만 넘어왔을 때 처리
	public String detail(Model model, @PathVariable("id") Integer id) {
		// service 에 3을 넣어서 호출
		Question question = questionService.getQuestion(id);
		model.addAttribute("question", question);
		return "question_detail";
	}
	
}