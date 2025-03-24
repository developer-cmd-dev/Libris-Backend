package com.developerDev.Libris.Controller;


import com.developerDev.Libris.Entity.User;
import com.developerDev.Libris.ExceptionHandler.CustomException;
import com.developerDev.Libris.Service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService){
        this.userService =userService;
    }




    @GetMapping("/get-user")
    public ResponseEntity<String> getUser(){
        Authentication authentication= SecurityContextHolder.getContext().getAuthentication();
        return new ResponseEntity<>(authentication.getName(),HttpStatus.OK);
    }

    @GetMapping("/get-all-users")
    public ResponseEntity<List<User>> getAllUser(){
        List<User> users = userService.getUsers();
        return new ResponseEntity<>(users,HttpStatus.OK);
    }



}
