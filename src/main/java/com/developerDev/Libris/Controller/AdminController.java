package com.developerDev.Libris.Controller;


import com.developerDev.Libris.Entity.User;
import com.developerDev.Libris.Service.AdminUserService;
import com.developerDev.Libris.Service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin")
public class AdminController {

    private final UserService userService;
    private final AdminUserService adminService;

    public AdminController(UserService userService, AdminUserService adminService) {
        this.userService = userService;
        this.adminService = adminService;
    }

    @GetMapping("/get-all-users")
    public ResponseEntity<List<User>> getAllUser(){
        List<User> users = adminService.getUsers();
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @PostMapping("/add-book")
    public void addBook(@RequestBody String bookName){

    }


}
