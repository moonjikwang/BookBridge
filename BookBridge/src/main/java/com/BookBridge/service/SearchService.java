package com.BookBridge.service;

import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import com.BookBridge.dto.BookDTO;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class SearchService {
	
    @Value("${kakao.apikey}")
    private String apiKey;

	public List<BookDTO> searchResult(String keyword){
		String reqURL = "https://dapi.kakao.com/v3/search/book?query="+keyword+"&sort=accuracy&page=1&size=10";
		
		RestTemplate restTemplate = new RestTemplate();

		HttpHeaders headers = new HttpHeaders();
		headers.set("Authorization", "KakaoAK "+apiKey);

		HttpEntity<String> entity = new HttpEntity<>("", headers);

		ResponseEntity<String> response = restTemplate.exchange(
			reqURL,
		    HttpMethod.GET,
		    entity,
		    String.class
		);

		String responseBody = response.getBody();
		
		ObjectMapper mapper = new ObjectMapper();
		JsonNode node = null;
			try {
				node = mapper.readTree(responseBody);
			} catch (JsonProcessingException e) {
				e.printStackTrace();
			}
		List<BookDTO> list = new ArrayList<BookDTO>();
		JsonNode documents = node.get("documents");
		for (JsonNode document : documents) {
			BookDTO dto = BookDTO.builder()
			.author(document.get("authors").toString())
			.title(document.get("title").toString())
			.thumbnail(document.get("thumbnail").toString())
			.publisher(document.get("publisher").toString())
			.contents(document.get("contents").toString())
			.build();
		    list.add(dto);
		}
		System.out.println(list);
		
		return list;
	}
}
