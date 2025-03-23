package com.developerDev.Libris.Controller;

import com.developerDev.Libris.Entity.User;
import com.developerDev.Libris.ExceptionHandler.CustomException;
import com.developerDev.Libris.Service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/public")
public class PublicController {

    private final UserService userService;
    public PublicController(UserService userService){
        this.userService= userService;
    }


    @GetMapping("/health-check")
    public ResponseEntity<String> healthCheck(){
        return new ResponseEntity<>("Welcome to the Libris Server.", HttpStatus.OK);
    }

    @PostMapping("/add-user")
    public ResponseEntity<String> addUser(@RequestBody User user){
        User response = userService.addUser(user);
        if(response==null){
            throw new CustomException("Something went wrong",HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>("User saved.",HttpStatus.OK);


    }

}
