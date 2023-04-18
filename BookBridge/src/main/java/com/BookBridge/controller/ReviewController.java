package com.BookBridge.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.BookBridge.dto.BookDTO;
import com.BookBridge.dto.ReviewDTO;
import com.BookBridge.service.ReviewService;
import com.BookBridge.service.SearchService;

@Controller
public class ReviewController {

	@Autowired
	SearchService searchService;

	@Autowired
	ReviewService reviewService;

	@GetMapping("/reviewList")
	public void reviewList() {
	}

	@GetMapping("/reviewWrite")
	public void reviewWrite(@RequestParam String isbn, Model model) {
		List<BookDTO> list = searchService.searchResult(isbn);
		BookDTO book = list.get(0);
		model.addAttribute("book", book);
	}

	@PostMapping("/reviewWrite")
	public String review(ReviewDTO dto, HttpServletResponse res) {
		PrintWriter printWriter = null;
		res.setCharacterEncoding("utf-8");
		res.setContentType("text/html;charset=utf-8");

		if (!reviewService.register(dto)) {
			try {
				printWriter = res.getWriter();
				printWriter.println(
						"<script type='text/javascript'>" + "alert('로그인에 실패했습니다.');" + "history.back();" + "</script>");
				printWriter.flush();
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				if (printWriter != null) {
					printWriter.close();
				}
			}
		}
		return "redirect:reviewList";
	}

}
