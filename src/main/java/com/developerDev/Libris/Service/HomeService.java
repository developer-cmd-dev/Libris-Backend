package com.developerDev.Libris.Service;

import com.developerDev.Libris.Entity.Books;
import com.developerDev.Libris.ExceptionHandler.CustomException;
import com.developerDev.Libris.JsonResposeEntity.BooksDataResponse;
import com.developerDev.Libris.JsonResposeEntity.Kittens;
import com.developerDev.Libris.Repository.BooksRepository;
import com.developerDev.Libris.Repository.BooksRepositoryImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureOrder;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.util.*;
import java.util.stream.Stream;

@Service
@Slf4j
public class HomeService {

    private final RestTemplate restTemplate;
    private final String url = "https://gutendex.com/books";
    private final BooksRepository booksRepository;
    private final Random randomValue = new Random();
    private final BooksRepositoryImpl dbQuery;



    public HomeService(RestTemplate restTemplate, BooksRepository booksRepository, BooksRepositoryImpl dbQuery) {
        this.restTemplate = restTemplate;
        this.booksRepository = booksRepository;
        this.dbQuery = dbQuery;
    }


    @Transactional
    public List<BooksDataResponse.Book> getAllBooks() {
        List<BooksDataResponse.Book> dbData = booksRepository.findAll();
        if (dbData.isEmpty() || dbData == null) {
            try {
                ResponseEntity<BooksDataResponse> response = restTemplate.exchange(url, HttpMethod.GET, null,
                        BooksDataResponse.class);
                List<BooksDataResponse.Book> listOfBooks = Objects.requireNonNull(response.getBody()).getResults();
                return listOfBooks.stream().map(books -> {
                    books.setPrice(randomValue.nextDouble(1000));
                    return booksRepository.save(books);
                }).toList();

            } catch (Exception e) {
                throw new CustomException("External API request failed . Unable to process request",
                        HttpStatus.BAD_REQUEST);
            }
        }

        return dbData;


    }


    @Transactional
    public List<BooksDataResponse.Book> searchBook(String query) {
         List<BooksDataResponse.Book> newSearchedBookList = new ArrayList<>();
        try {
            List<BooksDataResponse.Book> findInDb = dbQuery.findByTitle(query);
            if (findInDb.isEmpty()) {
                String searchUrl = url.replace("books", "books/?search=" + query.toLowerCase());
                ResponseEntity<BooksDataResponse> response = restTemplate.exchange(searchUrl, HttpMethod.GET, null,
                        BooksDataResponse.class);
                Objects.requireNonNull(response.getBody()).getResults().forEach((value) -> {
                    ObjectMapper objectMapper = new ObjectMapper();
                    Map<String, Object> bookMap = objectMapper.convertValue(value, Map.class);
                    if (bookMap.containsKey("formats")) {
                        Map<String, String> formats = (Map<String, String>) bookMap.get("formats");
                        Map<String, String> sanitizedFormats = new HashMap<>();
                        for (Map.Entry<String, String> entry : formats.entrySet()) {
                            String sanitizedKey = entry.getKey().replace(".", "_"); // Replace dots with "_"
                            sanitizedFormats.put(sanitizedKey, entry.getValue());
                        }
                        bookMap.put("formats", sanitizedFormats);
                        BooksDataResponse.Book editedValue = objectMapper.convertValue(bookMap,
                                BooksDataResponse.Book.class);
                        Optional<BooksDataResponse.Book> bookResponse = booksRepository.findById(editedValue.getId());
                        if (bookResponse.isEmpty()) {
                            System.out.println(editedValue.toString());
                            editedValue.setPrice(randomValue.nextDouble(1000));
                            BooksDataResponse.Book save = booksRepository.save(editedValue);
                            newSearchedBookList.add(save);
                        }else {
                            newSearchedBookList.add(bookResponse.get());
                        }

                    }
                });
                return newSearchedBookList;
            }

            return findInDb;

        } catch (Exception e) {
            throw new CustomException(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }


}
