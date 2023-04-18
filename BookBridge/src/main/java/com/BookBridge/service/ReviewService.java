package com.BookBridge.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.BookBridge.dto.ReviewDTO;
import com.BookBridge.entity.Member;
import com.BookBridge.entity.Review;
import com.BookBridge.repository.ReviewRepository;

@Service
public class ReviewService {

	@Autowired
	ReviewRepository reviewRepository;
	
	public boolean register(ReviewDTO dto) {
		if(dto == null || dto.getId() == null || dto.getIsbn() == null) {
			return false;
		}
		Review review = dtoToEntity(dto);
		Review entity = reviewRepository.save(review);
		return entity.getNo() != null;
	}
	
	private Review dtoToEntity(ReviewDTO dto) {
		Member mem = Member.builder().id(dto.getId()).build();
		return Review.builder()
				.isbn(dto.getIsbn())
				.member(mem)
				.rating(dto.getRating())
				.review(dto.getReview())
				.build();
	}
	private ReviewDTO entityToDto(Review entity) {
		return ReviewDTO.builder()
				.no(entity.getNo())
				.isbn(entity.getIsbn())
				.nickName(entity.getMember().getNickName())
				.rating(entity.getRating())
				.review(entity.getReview())
				.build();
	}
	
}
