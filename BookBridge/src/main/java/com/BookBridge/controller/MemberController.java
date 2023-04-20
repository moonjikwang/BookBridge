package com.BookBridge.controller;


import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.BookBridge.dto.MemberDTO;
import com.BookBridge.service.MemberService;

@Controller
public class MemberController {

	@Autowired
	MemberService memberService;
	
	private PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
	
	@PostMapping("login")
	public String login(MemberDTO dto, HttpServletRequest request, HttpServletResponse res) {
	    HttpSession session = request.getSession();
	    PrintWriter printWriter = null;
	    res.setCharacterEncoding("utf-8");
	    res.setContentType("text/html;charset=utf-8");
	    MemberDTO userInfo = memberService.login(dto.getEmail(), dto.getPassword(), passwordEncoder);
		if(userInfo != null) {
			session.setAttribute("userInfo", userInfo);
		}else {
			try {
				printWriter = res.getWriter();
	            printWriter.println("<script type='text/javascript'>"
	                    + "alert('로그인에 실패했습니다.');"
	                    + "history.back();"
	                    +"</script>");
	            printWriter.flush();
			} catch (IOException e) {
				e.printStackTrace();
			}finally {
	                if(printWriter != null) { printWriter.close(); }
	            
	        }
		}
		return "redirect:index";
	}
	
	@PostMapping("register")
	public String registerForm(MemberDTO dto,HttpServletRequest request,HttpServletResponse res) {
		dto.setPassword(passwordEncoder.encode(dto.getPassword()));
		HttpSession session = request.getSession();
	    PrintWriter printWriter = null;
	    res.setCharacterEncoding("utf-8");
	    res.setContentType("text/html;charset=utf-8");
		MemberDTO userInfo = memberService.register(dto);
		if(userInfo == null) {
			try {
				printWriter = res.getWriter();
	            printWriter.println("<script type='text/javascript'>"
	                    + "alert('회원가입에 실패했습니다. 이메일을 다시 확인해주세요.');"
	                    + "history.back();"
	                    +"</script>");
	            printWriter.flush();
			} catch (IOException e) {
				e.printStackTrace();
			}finally {
	                if(printWriter != null) { printWriter.close(); }
	            
	        }
		}
		session.setAttribute("userInfo",userInfo);
		return "redirect:index";
	}
	@GetMapping("userlogout")
	public String logout(HttpServletRequest request) {
		HttpSession session = request.getSession();
		session.invalidate();
		return "redirect:index";
	}
}
