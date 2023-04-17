package com.BookBridge.service;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.BookBridge.dto.BookDTO;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
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
		System.out.println(responseBody);
		
        ObjectMapper mapper = new ObjectMapper();
        List<BookDTO> books = null;
		try {
			books = mapper.readValue(responseBody, new TypeReference<List<BookDTO>>() {});
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        System.out.println(books);
		return null;
	}
}
