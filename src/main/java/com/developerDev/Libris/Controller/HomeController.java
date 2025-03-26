package com.developerDev.Libris.Controller;

import com.developerDev.Libris.JsonResposeEntity.Kittens;
import com.developerDev.Libris.Service.HomeService;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("home")
public class HomeController {

    private final HomeService homeService;

    public HomeController(HomeService homeService) {
        this.homeService = homeService;
    }


    @GetMapping
    public ResponseEntity<Kittens> getAllBooks(){
        Kittens res = homeService.getAllBooks();
        return new ResponseEntity<>(res, HttpStatus.OK);
    }
}
