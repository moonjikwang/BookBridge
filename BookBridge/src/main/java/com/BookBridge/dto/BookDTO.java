package com.BookBridge.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BookDTO {
	
    private String title;
    private String author;
    private String publisher;
    private String contents;
    private String thumbnail;
	
}
