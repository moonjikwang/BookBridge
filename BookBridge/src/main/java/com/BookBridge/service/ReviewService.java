package com.BookBridge.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.BookBridge.dto.BookDTO;
import com.BookBridge.dto.ReviewDTO;
import com.BookBridge.entity.Member;
import com.BookBridge.entity.Review;
import com.BookBridge.repository.ReviewRepository;

@Service
public class ReviewService {

	@Autowired
	ReviewRepository reviewRepository;
	@Autowired
	SearchService searchService;
	
	 public Page<ReviewDTO> getList(Pageable pageable) {
		 Page<Review> page = reviewRepository.findAll(pageable);
	     List<ReviewDTO> reviewDTOList = new ArrayList<>();
	     page.forEach(review -> {
	    	 ReviewDTO reviewDTO = entityToDto(review);
	    	 List<BookDTO> book = searchService.searchResult(review.getIsbn());
	    	 reviewDTO.setUrl(book.get(0).getUrl());
	    	 reviewDTO.setAuthor(book.get(0).getAuthor());
	    	 reviewDTO.setThumbnail(book.get(0).getThumbnail());
	    	 reviewDTO.setTitle(book.get(0).getTitle());
	    	 reviewDTOList.add(reviewDTO);
	     		});
		Page<ReviewDTO> pageDto = new PageImpl<>(reviewDTOList, pageable, page.getTotalElements());
		return pageDto;
	    }
	 public List<ReviewDTO> getNewList(){
		 List<ReviewDTO> reviewDTOList = new ArrayList<>();
		 List<Review> entityList = reviewRepository.findTop5ByOrderByRegDateDesc();
		 entityList.forEach(review -> {
			 ReviewDTO reviewDTO = entityToDto(review);
	    	 List<BookDTO> book = searchService.searchResult(review.getIsbn());
	    	 reviewDTO.setUrl(book.get(0).getUrl());
	    	 reviewDTO.setAuthor(book.get(0).getAuthor());
	    	 reviewDTO.setThumbnail(book.get(0).getThumbnail());
	    	 reviewDTO.setTitle(book.get(0).getTitle());
	    	 reviewDTOList.add(reviewDTO);
		 });
		 return reviewDTOList;
	 }
	 
	 
	 
	 public List<BookDTO> reviewTop5() {
		List<String> top5 = reviewRepository.reviewTop5();
		List<BookDTO> topBook = new ArrayList<>();
		top5.forEach(isbn ->{
			List<BookDTO> result = searchService.searchResult(isbn);
			topBook.add(result.get(0));
		});
		return topBook;
	 }
	
	
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
				.regDate(entity.getRegDate())
				.nickName(entity.getMember().getNickName())
				.rating(entity.getRating())
				.review(entity.getReview())
				.build();
	}
	
}
