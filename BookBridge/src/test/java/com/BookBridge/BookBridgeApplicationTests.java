package com.BookBridge;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.BookBridge.service.SearchService;

@SpringBootTest
class BookBridgeApplicationTests {

	@Autowired
	SearchService searchService;
	
	@Test
	void contextLoads() {
		searchService.searchResult("자바");
	}

}
