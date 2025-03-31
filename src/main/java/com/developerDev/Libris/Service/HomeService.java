package com.developerDev.Libris.Service;

import com.developerDev.Libris.Entity.Books;
import com.developerDev.Libris.ExceptionHandler.CustomException;
import com.developerDev.Libris.JsonResposeEntity.BooksDataResponse;
import com.developerDev.Libris.JsonResposeEntity.Kittens;
import com.developerDev.Libris.Repository.BooksRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Random;
import java.util.stream.Stream;

@Service
@Slf4j
public class HomeService {

    private final RestTemplate restTemplate;
    private final String url="https://gutendex.com/book";
    private final BooksRepository booksRepository;
    private final Random randomValue = new Random();


    public HomeService(RestTemplate restTemplate, BooksRepository booksRepository) {
        this.restTemplate = restTemplate;
        this.booksRepository = booksRepository;
    }


    @Transactional
    public List<BooksDataResponse.Book> getAllBooks(){
            List<BooksDataResponse.Book>dbData = booksRepository.findAll();
            if(dbData.isEmpty()|| dbData==null){
                   try {
                       ResponseEntity<BooksDataResponse> response = restTemplate.exchange(url, HttpMethod.GET,null,BooksDataResponse.class);
                       List<BooksDataResponse.Book> listOfBooks = Objects.requireNonNull(response.getBody()).getResults();
                       return listOfBooks.stream().map(books -> {
                           books.setPrice(randomValue.nextDouble(1000));
                           return booksRepository.save(books);
                       }).toList();

                   }catch (Exception e){
                       throw new CustomException("External API request failed . Unable to process request",
                               HttpStatus.BAD_REQUEST);
                   }
            }

        return dbData;


    }


}
