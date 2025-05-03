package com.developerDev.Libris.Controller;

import com.developerDev.Libris.Entity.User;
import com.developerDev.Libris.ExceptionHandler.CustomException;
import com.developerDev.Libris.JsonResposeEntity.BooksDataResponse;
import com.developerDev.Libris.JsonResposeEntity.Kittens;
import com.developerDev.Libris.JsonResposeEntity.RentedBooksData;
import com.developerDev.Libris.Service.HomeService;
import com.developerDev.Libris.Service.RentBookService;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Marker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/home")
public class HomeController {

    private final HomeService homeService;
    private final RentBookService rentBookService;

    @Autowired
    public HomeController(HomeService homeService, RentBookService rentBookService) {
        this.homeService = homeService;
        this.rentBookService = rentBookService;
    }


    @GetMapping
    public ResponseEntity<List<BooksDataResponse.Book>> getAllBooks() {
        List<BooksDataResponse.Book> res = homeService.getAllBooks();
        System.out.println(res.size());
        if (res != null) {
            return new ResponseEntity<>(res, HttpStatus.OK);
        }
        throw new CustomException("Something went wrong!", HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @PostMapping("/rent-book/{bookId}")
    public ResponseEntity<List<RentedBooksData>> rentBook(@RequestBody RentedBooksData data,
                                                          @PathVariable String bookId) {

        User user = rentBookService.rentBook(data, bookId);

        return new ResponseEntity<>(user.getRentedBooks(), HttpStatus.OK);
    }

    @PostMapping("/return-book/{bookId}")
    public ResponseEntity<  List<Object>> returnBook(@PathVariable String bookId) {
        List<Object> response = rentBookService.returnBook(bookId);
        assert response != null;
        return new ResponseEntity<>(response, HttpStatus.OK);

    }

    @GetMapping("/get-rented-book")
    public ResponseEntity<List<RentedBooksData>> getRentedBooksData(){
        List<RentedBooksData> response = rentBookService.getRentedBooksData();
        return new ResponseEntity<>(response,HttpStatus.OK);
    }

    @GetMapping("/search")
    public ResponseEntity<List<BooksDataResponse.Book>> searchBook(@RequestParam(name = "title") String value) {
        log.info(value);
        List<BooksDataResponse.Book> response = homeService.searchBook(value);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }


}
