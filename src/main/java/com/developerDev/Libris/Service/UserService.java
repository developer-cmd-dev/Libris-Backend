package com.developerDev.Libris.Service;

import com.developerDev.Libris.Entity.Books;
import com.developerDev.Libris.Entity.User;
import com.developerDev.Libris.ExceptionHandler.CustomException;
import com.developerDev.Libris.Repository.BooksRepository;
import com.developerDev.Libris.Repository.UserReopository;
import com.developerDev.Libris.Utils.JWTUtil;
import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class UserService {


    private final UserReopository userRepository;
    private final AuthenticationManager authenticationManager;
    private final UserDetailServiceImpl userDetailService;
    private final JWTUtil jwtUtil;
    private static final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
    private final BooksRepository booksRepository;



    public UserService(UserReopository userRepository, AuthenticationManager authenticationManager, UserDetailServiceImpl userDetailService, JWTUtil jwtUtil, BooksRepository booksRepository){
        this.userRepository = userRepository;
        this.authenticationManager = authenticationManager;
        this.userDetailService = userDetailService;
        this.jwtUtil = jwtUtil;
        this.booksRepository = booksRepository;
    }


    public User addUser (User user){
        User isUserAvail= userRepository.findByEmail(user.getEmail()).orElse(null);
        if(isUserAvail==null){
           user.setRoles(user.getRoles().stream().map(String::toUpperCase).collect(Collectors.toList()));
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            return userRepository.save(user);
        }
        return isUserAvail;
    }

    public String loginUser(User user){
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(user.getEmail(),user.getPassword()));
        UserDetails userDetails =  userDetailService.loadUserByUsername(user.getEmail());
        return  jwtUtil.generateToken(userDetails.getUsername());
    }

    public User buyBook(Books books,String username){
        Books savedBooks = booksRepository.save(books);
        User findUser=userRepository.findByEmail(username).orElse(null);
        if(findUser!=null){
            List<ObjectId> list = findUser.getBooksData();
            list.add(savedBooks.getId());
            findUser.setBooksData(list);
           return userRepository.save(findUser);
        }

        throw new CustomException("Something wrong to buy book!",HttpStatus.BAD_REQUEST);

    }





}
