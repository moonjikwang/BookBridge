package com.BookBridge;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.BookBridge.service.ReviewService;
import com.BookBridge.service.SearchService;

@SpringBootTest
class BookBridgeApplicationTests {

	@Autowired
	ReviewService reviewService;
	
	@Test
	void contextLoads() throws Exception {
		
	}

}
