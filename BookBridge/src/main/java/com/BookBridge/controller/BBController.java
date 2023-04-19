package com.BookBridge.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.BookBridge.dto.BookDTO;
import com.BookBridge.dto.ReviewDTO;
import com.BookBridge.service.ReviewService;

@Controller
public class BBController {

	@Autowired
	private ReviewService reviewService;
	
	@GetMapping("")
	public String home() {
		return "redirect:index";
	}
	@GetMapping("about")
	public void about() {
		
	}
	
	@GetMapping("/index")
	public void index(Model model) {
		List<BookDTO> top5 = reviewService.reviewTop5();
		List<ReviewDTO> newReview = reviewService.getNewList();
		model.addAttribute("topBook",top5);
		model.addAttribute("reviewList",newReview);
	}

}
