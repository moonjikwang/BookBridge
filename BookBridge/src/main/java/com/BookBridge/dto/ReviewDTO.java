package com.BookBridge.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReviewDTO {

	private Long no; //리뷰번호
	// 아래 섬네일,타이틀,저자는 db의 도서번호를 기준으로 조회해서 dto에 넣어서받을예정
    private String isbn; //도서번호
    private String thumbnail; //섬네일
    private String title; // 타이틀
    private String author; //저자
    
    private String id;
    private String review;
    private int rating;
    private String nickName; 
}
