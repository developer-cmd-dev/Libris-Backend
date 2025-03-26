package com.developerDev.Libris.Service;

import com.developerDev.Libris.Entity.User;
import com.developerDev.Libris.Repository.UserReopository;
import com.developerDev.Libris.Utils.JWTUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class UserService {


    private final UserReopository reopository;
    private final AuthenticationManager authenticationManager;
    private final UserDetailServiceImpl userDetailService;
    private final JWTUtil jwtUtil;
    private static final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();


    public UserService(UserReopository reopository, AuthenticationManager authenticationManager, UserDetailServiceImpl userDetailService, JWTUtil jwtUtil){
        this.reopository = reopository;
        this.authenticationManager = authenticationManager;
        this.userDetailService = userDetailService;
        this.jwtUtil = jwtUtil;
    }


    public User addUser (User user){
        User isUserAvail= reopository.findByEmail(user.getEmail()).orElse(null);
        if(isUserAvail==null){
           user.setRoles(user.getRoles().stream().map(String::toUpperCase).collect(Collectors.toList()));
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            return reopository.save(user);
        }
        return isUserAvail;
    }

    public String loginUser(User user){
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(user.getEmail(),user.getPassword()));
        UserDetails userDetails =  userDetailService.loadUserByUsername(user.getEmail());
        return  jwtUtil.generateToken(userDetails.getUsername());
    }





}
