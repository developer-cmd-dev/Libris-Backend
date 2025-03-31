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
import org.springframework.beans.factory.annotation.Autowired;
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
    private final RentBookService rentBookService;

    @Autowired
    public HomeController(HomeService homeService, RentBookService rentBookService) {
        this.homeService = homeService;
        this.rentBookService = rentBookService;
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
    public  ResponseEntity<List<RentedBooksData>> rentBook(@RequestBody RentedBooksData data, @PathVariable String bookId){
        User user = rentBookService.rentBook(data,bookId);
        return new ResponseEntity<>(user.getRentedBooks(),HttpStatus.OK);
    }

    @GetMapping("/search")
    public ResponseEntity<BooksDataResponse> searchBook(@RequestParam(name="title") String value){
        log.info(value);
        BooksDataResponse response = homeService.searchBook(value);
        return new ResponseEntity<>(response,HttpStatus.OK);
    }

    @GetMapping("/search/findByTitle")
    public ResponseEntity<List<BooksDataResponse.Book>> findBytitle(){
        return new ResponseEntity<>(homeService.find(),HttpStatus.OK) ;
    }


}
