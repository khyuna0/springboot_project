package com.khyuna0.khyuna0board.question;

import java.security.Principal;
import java.util.List;

import org.glassfish.jaxb.core.v2.TODO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.khyuna0.khyuna0board.answer.AnswerForm;
import com.khyuna0.khyuna0board.answer.AnswerService;
import com.khyuna0.khyuna0board.user.SiteUser;
import com.khyuna0.khyuna0board.user.UserService;

import jakarta.validation.Valid;

@RequestMapping("/question")
@Controller
public class QuestionController {

    private final AnswerService answerService;
	
	@Autowired
	private QuestionRepository questionRepository;
	
	@Autowired
	private QuestionService questionService;
	
	@Autowired
	private UserService userService;

    QuestionController(AnswerService answerService) {
        this.answerService = answerService;
    }
	
	/*
	@GetMapping(value = "/list") // value 생략 가능
	//@ResponseBody
	public String list(Model model, @RequestParam(value ="page", defaultValue = "0") int page) {
		//List<Question> questionlist = questionRepository.findAll();		
		//List<Question> questionlist = questionService.getList();

		Page<Question> paging =questionService.getList(page);
		// 1게시글 10개씩 자른 리스트 (페이지 당 10개)
		model.addAttribute("questionList", paging);
		return "question_list";
	}
	*/
	
	@GetMapping(value = "/list") // value 생략 가능
	//@ResponseBody
	public String list(Model model) {
		//List<Question> questionlist = questionRepository.findAll();		
		//List<Question> questionlist = questionService.getList();

		List<Question> questionlist = questionService.getList();
		// 1게시글 10개씩 자른 리스트 (페이지 당 10개)
		model.addAttribute("questionList", questionlist);
		return "question_list";
	}
	
	
	@GetMapping(value = "/detail/{id}") // 파라미터 이름 없이 값만 넘어왔을 때 처리
	public String detail(Model model, @PathVariable("id") Integer id, AnswerForm answerForm) {
		// service 에 3을 넣어서 호출
		Question question = questionService.getQuestion(id);
		model.addAttribute("question", question);
		return "question_detail";
	}
	
	// 같은 요청이지만 get/post 방식에 따라 다르게 설정 가능
	// 질문 등록 폼 매핑만
	@GetMapping(value = "/create")
	public String questionCreate(QuestionForm questionForm) {
		return "question_form"; 
	}
	
//	// 질문 내용을 DB에 저장하는 메서드
//	// 메서드 오버라이딩
//	@PostMapping("/create") 
//	public String questionCreate(@RequestParam(value="subject") String subject, @RequestParam(value="content") String content) {
//		// @RequestParam(value="subject") String subject -> String subject = request.getparameter("subject") 
//		// @RequestParam(value="content") String content -> String content = request.getparameter("content") 
//		
//		
//		questionService.create(subject, content);
//		return "redirect:/question/list"; // 질문 리스트로 이동 (redirect 필수 - refresh)  
//	}
	
	// 유효성체크
	@PostMapping(value = "/create") 
	public String questionCreate(@Valid QuestionForm questionForm, BindingResult bindingResult, Principal principal) {
		
		if(bindingResult.hasErrors()) { // 참이면 유효성 체크에서 에러 발생함
			return "question_form"; // 에러 발생 시 다시 질문 폼으로 이동
		}
		SiteUser siteUser = userService.getUser(principal.getName());
		// 현재 로그인된 유저의 username으로 엔티티 받기
		
		questionService.create(questionForm.getSubject(), questionForm.getContent(), siteUser);
		return "redirect:/question/list"; // 질문 리스트로 이동 (redirect 필수 - refresh)  
	}
	
}