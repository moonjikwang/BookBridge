package com.BookBridge.controller;


import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.BookBridge.dto.MemberDTO;
import com.BookBridge.service.MemberService;

@Controller
public class MemberController {

	@Autowired
	MemberService memberService;
	
	
	
	@PostMapping("login")
	public String login(MemberDTO dto,HttpServletRequest request,HttpServletResponse res) {
		HttpSession session = request.getSession();
		PrintWriter printWriter = null;
        res.setCharacterEncoding("utf-8");
        res.setContentType("text/html;charset=utf-8");
		MemberDTO userInfo = memberService.login(dto);
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
	@GetMapping("register")
	public void register() {
	}
	
	@PostMapping("register")
	public String registerForm(MemberDTO dto,HttpServletRequest request) {
		HttpSession session = request.getSession();
		MemberDTO userInfo = memberService.register(dto);
		session.setAttribute("userInfo",userInfo);
		return "redirect:index";
	}
	@GetMapping("logout")
	public String logout(HttpServletRequest request) {
		HttpSession session = request.getSession();
		session.invalidate();
		return "redirect:index";
	}
}
