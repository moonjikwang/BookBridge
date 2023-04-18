package com.BookBridge.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.BookBridge.entity.Review;

public interface ReviewRepository extends JpaRepository<Review, Long>{

}
