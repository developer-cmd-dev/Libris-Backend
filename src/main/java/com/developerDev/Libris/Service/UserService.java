package com.developerDev.Libris.Service;

import com.developerDev.Libris.Entity.User;
import com.developerDev.Libris.Repository.UserReopository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {


    private final UserReopository reopository;
    private static final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public UserService(UserReopository reopository){
        this.reopository = reopository;
    }


    public User addUser (User user){
        User isUserAvail= reopository.findByEmail(user.getEmail()).orElse(null);
        if(isUserAvail==null){
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            return reopository.save(user);
        }
        return isUserAvail;
    }

    public List<User> getUsers(){
        return reopository.findAll();
    }



}
