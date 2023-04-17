package com.BookBridge.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;

import com.BookBridge.dto.BookDTO;
import com.BookBridge.service.SearchService;


@Controller
public class SearchController {

	@Autowired
	SearchService searchService;
	
	@PostMapping("search")
	public void search(String keyword,Model model) {
		List<BookDTO> list = searchService.searchResult(keyword);
		model.addAttribute("keyword",keyword);
		model.addAttribute("list",list);
	}
}
