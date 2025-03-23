package com.developerDev.Libris.Controller;


import com.developerDev.Libris.Entity.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/public")
public class UserController {


    @PostMapping("/add-user")
    public ResponseEntity<String> addUser(@RequestBody User user){
        log.info(user.toString());
        return new ResponseEntity<>("User data received", HttpStatus.OK);

    }


}
