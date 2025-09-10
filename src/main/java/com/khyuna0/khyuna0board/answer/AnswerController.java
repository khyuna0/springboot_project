package com.khyuna0.khyuna0board.answer;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.server.ResponseStatusException;

import com.khyuna0.khyuna0board.question.Question;
import com.khyuna0.khyuna0board.question.QuestionService;
import com.khyuna0.khyuna0board.user.SiteUser;
import com.khyuna0.khyuna0board.user.UserService;

import jakarta.validation.Valid;
@RequestMapping("/answer")
@Controller
public class AnswerController {

    private final AnswerService answerService;

	@Autowired
	private QuestionService questionService;
	
	@Autowired
	private UserService userService;
	
    AnswerController(AnswerService answerService) {
        this.answerService = answerService;
    }
    @PreAuthorize("isAuthenticated()") // 로그인한 사용자가 아니면 로그인 화면으로 강제 이동
    @PostMapping(value = "/create/{id}") //답변 등록 요청 -> 오는 파라미터 값 : 부모 질문글의 번호
	public String createAnswer(Model model, @PathVariable("id") Integer id, @Valid AnswerForm answerForm,  BindingResult bindingResult, Principal principal) {
		Question question = questionService.getQuestion(id);	
		// principal.getName(); // 로그인한 유저의 아이디 얻기
		
		SiteUser siteUser = userService.getUser(principal.getName());
		if(bindingResult.hasErrors()) {
			model.addAttribute("question", question); 
			return "question_detail";
		}  
		answerService.create(question,answerForm.getContent(), siteUser ); //DB에 답변 등록
		
		return String.format("redirect:/question/detail/%s", id);  
	}
    
    @PreAuthorize("isAuthenticated()") // 로그인한 사용자가 아니면 로그인 화면으로 강제 이동
    @GetMapping(value = "/modify/{id}")
    public String answerModify(@PathVariable("id") Integer id, @Valid AnswerForm answerForm,  BindingResult bindingResult, Principal principal) {
    	Answer answer = answerService.getAnswer(id);
    	if(!answer.getAuthor().equals(principal.getName())) { // 참이면 수정권한 없음
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "수정 권한이 없습니다.");
		}
		answerForm.setContent(answer.getContent()); // model로 보내지 않아도 answerForm 이 전송됨
    	
    	return "answer_form";
    }
    
    
    
}
