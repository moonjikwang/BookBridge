package com.BookBridge.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.BookBridge.dto.BookDTO;
import com.BookBridge.dto.MemberDTO;
import com.BookBridge.dto.ReviewDTO;
import com.BookBridge.service.ReviewService;
import com.BookBridge.service.SearchService;

@Controller
public class ReviewController {

	@Autowired
	SearchService searchService;

	@Autowired
	ReviewService reviewService;

	@GetMapping("/reviewList")
	public void reviewList(Pageable pageable, Model model) {
		Page<ReviewDTO> reviews = reviewService
				.getList(PageRequest.of(pageable.getPageNumber(), 9, Sort.by("regDate").descending()));
		model.addAttribute("reviews", reviews);
	}

	@GetMapping("/reviewWrite")
	public String reviewWrite(@RequestParam String isbn, Model model, HttpSession session, HttpServletResponse response) {
	    MemberDTO userInfo = (MemberDTO) session.getAttribute("userInfo");
	    if (userInfo == null) {
	        // 로그인되지 않은 사용자이므로 인덱스 페이지로 리다이렉트 및 alert
	        try {
	            response.setContentType("text/html;charset=UTF-8");
	            PrintWriter out = response.getWriter();
	            out.println("<script>alert('로그인 후 이용 가능합니다.'); location.href='index';</script>");
	            out.flush();
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
	        return null; // null을 반환하여 뷰 페이지를 반환하지 않습니다.
	    }
	    List<BookDTO> list = searchService.searchResult(isbn);
	    BookDTO book = list.get(0);
	    model.addAttribute("book", book);
	    
	    return "reviewWrite"; 
	}
	@GetMapping("reviewModify")
	public String reveiwModify(@RequestParam Long no, Model model, HttpSession session, HttpServletResponse response) {
		MemberDTO userInfo = (MemberDTO) session.getAttribute("userInfo");
		ReviewDTO dto = reviewService.findReview(no);
	    if (userInfo == null || userInfo.getId() == dto.getId()) {
	        // 로그인되지 않은 사용자이므로 인덱스 페이지로 리다이렉트 및 alert
	        try {
	            response.setContentType("text/html;charset=UTF-8");
	            PrintWriter out = response.getWriter();
	            out.println("<script>alert('글쓴이만 수정 가능합니다.'); location.href='index';</script>");
	            out.flush();
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
	        return null; // null을 반환하여 뷰 페이지를 반환하지 않습니다.
	    }
	    model.addAttribute("book", dto);
	    
	    return "reviewModify"; 
	}
	@GetMapping("reviewDelete")
	public String reviewDelete(@RequestParam Long no,HttpSession session, HttpServletResponse response) {
		MemberDTO userInfo = (MemberDTO) session.getAttribute("userInfo");
		ReviewDTO dto = reviewService.findReview(no);
	    if (userInfo == null || userInfo.getId() == dto.getId()) {
	        // 로그인되지 않은 사용자이므로 인덱스 페이지로 리다이렉트 및 alert
	        try {
	            response.setContentType("text/html;charset=UTF-8");
	            PrintWriter out = response.getWriter();
	            out.println("<script>alert('글쓴이만 삭제 가능합니다.'); location.href='index';</script>");
	            out.flush();
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
	        return null; // null을 반환하여 뷰 페이지를 반환하지 않습니다.
	    }
	    reviewService.delete(no);
	    
	    return "redirect:reviewList"; 
	
	}

	@PostMapping("/reviewWrite")
	public String review(ReviewDTO dto, HttpServletResponse res) {
		PrintWriter printWriter = null;
		res.setCharacterEncoding("utf-8");
		res.setContentType("text/html;charset=utf-8");

		if (!reviewService.register(dto)) {
			try {
				printWriter = res.getWriter();
				printWriter.println(
						"<script type='text/javascript'>" + "alert('등록에 실패했습니다.');" + "history.back();" + "</script>");
				printWriter.flush();
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				if (printWriter != null) {
					printWriter.close();
				}
			}
		}
		return "redirect:reviewList";
	}

}
