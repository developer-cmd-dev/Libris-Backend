package com.developerDev.Libris.Service;

import com.developerDev.Libris.ExceptionHandler.CustomException;
import com.developerDev.Libris.JsonResposeEntity.BooksDataResponse;
import com.developerDev.Libris.JsonResposeEntity.Kittens;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@Slf4j
public class HomeService {

    private final RestTemplate restTemplate;
    private final String url="https://gutendex.com/books";


    public HomeService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }


    public BooksDataResponse getAllBooks(){

        try{
            ResponseEntity<BooksDataResponse> response = restTemplate.exchange(url, HttpMethod.GET,null,BooksDataResponse.class);
            return response.getBody();
        }catch (Exception ex){
            throw new CustomException("Failed to fetch Book data", HttpStatus.SERVICE_UNAVAILABLE);
        }

    }


}
