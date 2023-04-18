package com.BookBridge.entity;


import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Review extends BaseEntity{

	@Id
	private Long no;
	private String isbn; //도서번호
	private String review;
	private int rating;
	@ManyToOne(fetch = FetchType.LAZY)
	private Member member;
	
}
