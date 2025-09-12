package com.khyuna0.khyuna0board;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {
	
	@GetMapping(value = "/khBoard") // root 요청 처리 (클라우드용)
	//@GetMapping(value = "/") // root 요청 처리 (로컬용)
	public String root(Model model) {
		
		return "redirect:/question/list";
	}
}
