package com.BookBridge.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.BookBridge.dto.MemberDTO;
import com.BookBridge.entity.Member;
import com.BookBridge.repository.MemberRepository;

import lombok.extern.log4j.Log4j2;

@Service
@Log4j2
public class MemberService {

	@Autowired
	MemberRepository memberRepository;
	
	public MemberDTO login(final MemberDTO dto) {
		if(memberRepository.existsByEmail(dto.getEmail())) {
			Member member = memberRepository.findByEmailAndPassword(dto.getEmail(), dto.getPassword());
			if(member != null) {
				return entityToDto(member);
			}
		}
		return null;
	}
	
	public MemberDTO register(final MemberDTO dto) {
		if(dto == null || dto.getEmail() == null) {
			throw new RuntimeException("사용자 정보가 없음");
		}
		final String email = dto.getEmail();
		if(memberRepository.existsByEmail(email)) {
			log.warn("이미 존재하는 이메일임");
			throw new RuntimeException("이미 존재하는 이메일!!");
		}
		//위 검증에 문제가 없으면  저장 및 userEntity 리턴
		Member member = memberRepository.save(dtoToEntity(dto));
		return entityToDto(member);
	}
	
	private Member dtoToEntity(MemberDTO dto) {
		return Member.builder()
				.email(dto.getEmail())
				.password(dto.getPassword())
				.id(dto.getId())
				.nickName(dto.getNickName())
				.build();
	}
	private MemberDTO entityToDto(Member entity) {
		return MemberDTO.builder()
				.email(entity.getEmail())
				.id(entity.getId())
				.nickName(entity.getNickName())
				.build();
	}
		
}
