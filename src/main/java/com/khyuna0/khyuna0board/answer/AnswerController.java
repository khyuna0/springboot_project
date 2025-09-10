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
    @GetMapping(value = "/modify/{id}") // form 으로 이동하는 요청
    public String answerModify(@PathVariable("id") Integer id, AnswerForm answerForm, Principal principal) {
    	Answer answer = answerService.getAnswer(id);
    	if(!answer.getAuthor().getUsername().equals(principal.getName())) { // 참이면 수정권한 없음
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "수정 권한이 없습니다.");
		}
		answerForm.setContent(answer.getContent()); // model 로 보내지 않아도 answerForm 이 전송됨
    	
    	return "answer_form";
    }
    
    @PreAuthorize("isAuthenticated()") // 로그인한 사용자가 아니면 로그인 화면으로 강제 이동
    @PostMapping(value = "/modify/{id}") // 질문 수정하기 (DB 업데이트)
    public String answerModify(@PathVariable("id") Integer id, @Valid AnswerForm answerForm,  BindingResult bindingResult, Principal principal) {
    	Answer answer = answerService.getAnswer(id); // 원본 불러옴
    	if(bindingResult.hasErrors()) { 
    		return "answer_form";
    	}
    	if(!answer.getAuthor().getUsername().equals(principal.getName())) { // 참이면 수정권한 없음
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "수정 권한이 없습니다.");
		}
    	
    	answerService.answerModify(answer, answerForm.getContent() );
    	return String.format("redirect:/question/detail/%s", answer.getQuestion().getId());
    	// redirect -> 부모글(해당 답변이 달린 질문글)의 번호로 이동
    }
    
    @PreAuthorize("isAuthenticated()") // 로그인한 사용자가 아니면 로그인 화면으로 강제 이동
    @GetMapping(value = "/delete/{id}") // 삭제하기
    public String answerDelete(@PathVariable("id") Integer id, AnswerForm answerForm, Principal principal) {
    	Answer answer = answerService.getAnswer(id); // 삭제할 답변 엔티티
    	if(!answer.getAuthor().getUsername().equals(principal.getName())) { // 참이면 권한 없음
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "삭제 권한이 없습니다.");
		}
    	answerService.answerDelete(answer);
    	return String.format("redirect:/question/detail/%s", answer.getQuestion().getId());
    }
    
    @PreAuthorize("isAuthenticated()")
	@GetMapping(value="/vote/{id}")
	public String answerVote(@PathVariable("id") Integer id, Principal principal) {
    	Answer answer = answerService.getAnswer(id); // 유저가 추천한 질문 글의 엔티티 조회
		SiteUser siteUser = userService.getUser(principal.getName()); // 로그인한 유저의 아이디로 유저 엔티티 조회하기
		
		answerService.answerVote(answer , siteUser);
		return String.format("redirect:/question/detail/%s", answer.getQuestion().getId());
	}
    
}
