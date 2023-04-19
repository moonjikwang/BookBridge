package com.BookBridge.repository;

import java.util.List;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.BookBridge.entity.Review;

public interface ReviewRepository extends JpaRepository<Review, Long>{
    @Query("SELECT r.isbn FROM Review r GROUP BY r.isbn ORDER BY COUNT(r.isbn) DESC")
    List<String> findTop5ByOrderByIsbnDesc(Pageable pageable);
    
    default List<String> reviewTop5(){
        return findTop5ByOrderByIsbnDesc(PageRequest.of(0, 5));
    }
    
    List<Review> findTop5ByOrderByRegDateDesc();
}
