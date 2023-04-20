package com.BookBridge.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.BookBridge.entity.Member;

public interface MemberRepository extends JpaRepository<Member, String>{

	Boolean existsByEmail(String email); 
	Member findByEmailAndPassword(String email,String password);
	Member findByEmail(String email);
}
