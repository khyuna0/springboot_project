package com.khyuna0.khyuna0board.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import jakarta.validation.Valid;

@RequestMapping("/user")
@Controller
public class UserController {

	@Autowired
	private UserService userService;
	
	@GetMapping(value = "/signup") // 회원가입 폼 매핑
	public String signup(UserCreateForm userCreateForm) {
		return "signup_form";
	}
	
	@PostMapping(value = "/signup") // 회원 가입 처리 (DB 입력)
	public String signup(@Valid UserCreateForm userCreateForm, BindingResult bindingResult) {
		
		if(bindingResult.hasErrors()) { // 회원 정보 중 에러 발생
			return "signup_form";
		} 
		
		// 비밀번호 확인 실패 에러(UserCreateForm에 없는 에러) -> bindingResult에 추가
		if(!userCreateForm.getPassword1().equals(userCreateForm.getPassword2())) { // 비밀번호 확인 불일치
			bindingResult.rejectValue("password2", "passwordInCorrect", "비밀번호 확인이 일치하지 않습니다.");
			return "signup_form";
		}
		try {
			userService.create(userCreateForm.getUsername(), userCreateForm.getEmail(), userCreateForm.getPassword1() );
			
			
		} catch (DataIntegrityViolationException e) { // 중복된 데이터에 대한 예외처리
			e.printStackTrace();
			// 이미 등록된 사용자 아이디의 경우 발생하는 에러 추가
			bindingResult.rejectValue("signupFailed", "이미 등록된 사용자입니다.");
			return "signup_form";
		} catch (Exception e) { // 기타 나머지 예외처리
			e.printStackTrace();
			bindingResult.rejectValue("signupFailed", "회원 가입 실패입니다");
			return "signup_form";
		}
		
		return "redirect:/question/list"; // 첫 화면으로 이동
	}
	
	
}
