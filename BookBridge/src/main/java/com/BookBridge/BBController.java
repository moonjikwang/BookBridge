package com.BookBridge;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class BBController {

	@GetMapping("")
	public String home() {
		return "redirect:index";
	}
	
	@GetMapping("/index")
	public void index() {
	}
}
