package com.developerDev.Libris.Controller;

import com.developerDev.Libris.Entity.User;
import com.developerDev.Libris.ExceptionHandler.CustomException;
import com.developerDev.Libris.Repository.UserReopository;
import com.developerDev.Libris.Service.UserDetailServiceImpl;
import com.developerDev.Libris.Service.UserService;
import com.developerDev.Libris.Utils.JWTUtil;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/public")
public class PublicController {

    private static final Logger log = LoggerFactory.getLogger(PublicController.class);
    private final UserService userService;
    private final AuthenticationManager authenticationManager;
    private final UserDetailServiceImpl userDetailService;
    private final JWTUtil jwtUtil;


    public PublicController(UserService userService, AuthenticationManager authenticationManager,
                            UserDetailServiceImpl userDetailService, JWTUtil jwtUtil) {
        this.userService = userService;
        this.authenticationManager = authenticationManager;
        this.userDetailService = userDetailService;
        this.jwtUtil = jwtUtil;
    }


    @GetMapping("/health-check")
    public ResponseEntity<String> healthCheck() {
        return new ResponseEntity<>("Welcome to the Libris Server.", HttpStatus.OK);
    }

    @PostMapping("/signup")
    public ResponseEntity<String> signup(@RequestBody User user) {

        User response = userService.addUser(user);
        if (response == null) {
            throw new CustomException("Something went wrong", HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>("User saved.", HttpStatus.OK);

    }

    @PostMapping("/login")
    public ResponseEntity<Map<String,Object>> login(@RequestBody User user, HttpServletResponse response) {
        Map<String,Object> res = userService.loginUser(user);
        Cookie cookie = new Cookie("access_token",(String) res.get("access_token"));
        cookie.setHttpOnly(true);
        cookie.setPath("/");
        response.addCookie(cookie);
        return new ResponseEntity<>(res, HttpStatus.OK);


    }


    @GetMapping("/verify-token")
    public ResponseEntity<User> verifyToken(){
       String username= SecurityContextHolder.getContext().getAuthentication().getName();
       User user = userService.verifyToken(username);
        return new ResponseEntity<>(user,HttpStatus.OK);
    }


}
