package com.developerDev.Libris.Controller;


import com.developerDev.Libris.Entity.Books;
import com.developerDev.Libris.Entity.User;
import com.developerDev.Libris.ExceptionHandler.CustomException;
import com.developerDev.Libris.Service.UserService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

@Slf4j
@RestController
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService){
        this.userService =userService;
    }
    Supplier<String> getAuthenticatedUsername=()->SecurityContextHolder.getContext().getAuthentication().getName();


    @GetMapping("/get-user")
    public ResponseEntity<String> getUser(){

        return new ResponseEntity<>(getAuthenticatedUsername.get(),HttpStatus.OK);
    }


    @PostMapping("/save-book")
    public ResponseEntity<User> saveBook(@RequestBody String bookId){

        User response = userService.saveBook(Integer.parseInt(bookId),getAuthenticatedUsername.get());
        return new ResponseEntity<>(response,HttpStatus.OK);
    }

    @DeleteMapping("/delete-saved-book")
    public ResponseEntity<User> deleteSavedBook(@RequestBody String bookId){
        User response = userService.deleteBook(Integer.parseInt(bookId),getAuthenticatedUsername.get());
        return new ResponseEntity<>(response,HttpStatus.OK);
    }

    @PostMapping("/update-password")
    public ResponseEntity<String> updatePassword(@RequestBody String newPassword, HttpServletResponse response){
        response.setHeader("cookie",userService.updatePassword(newPassword));
        return new ResponseEntity<>("Password changed and new token validate.",HttpStatus.OK);
    }





}
