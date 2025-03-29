package com.developerDev.Libris.Controller;

import com.developerDev.Libris.ExceptionHandler.CustomException;
import com.developerDev.Libris.JsonResposeEntity.BooksDataResponse;
import com.developerDev.Libris.JsonResposeEntity.Kittens;
import com.developerDev.Libris.JsonResposeEntity.RentedBooksData;
import com.developerDev.Libris.Service.HomeService;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("home")
public class HomeController {

    private final HomeService homeService;

    public HomeController(HomeService homeService) {
        this.homeService = homeService;
    }


    @GetMapping
    public ResponseEntity<List<BooksDataResponse.Book>> getAllBooks(){
        List<BooksDataResponse.Book> res = homeService.getAllBooks();
        if (res!=null){
            return new ResponseEntity<>(res, HttpStatus.OK);
        }
        throw new CustomException("Something went wrong!",HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @PostMapping("/rent-book/{bookId}")
    public  ResponseEntity<?> rentBook(@RequestBody RentedBooksData data, @PathVariable String bookId){
        log.info(data.toString());
        log.info(bookId);
        return new ResponseEntity<>("got book data",HttpStatus.OK);
    }
}
