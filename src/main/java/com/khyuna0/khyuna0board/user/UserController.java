package com.khyuna0.khyuna0board.user;

import org.springframework.beans.factory.annotation.Autowired;
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
		
		userService.create(userCreateForm.getUsername(), userCreateForm.getEmail(), userCreateForm.getPassword1() );
		return "redirect:/"; // 첫 화면으로 이동
	}
	
	
}
