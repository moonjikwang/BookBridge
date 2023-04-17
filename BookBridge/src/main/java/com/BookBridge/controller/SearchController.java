package com.BookBridge.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;

import com.BookBridge.service.SearchService;

@Controller
public class SearchController {

	@Autowired
	SearchService searchService;
	
	@PostMapping("search")
	public void search() {
	}
}
