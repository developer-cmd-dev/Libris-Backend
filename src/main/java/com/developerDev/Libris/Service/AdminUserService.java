package com.developerDev.Libris.Service;


import com.developerDev.Libris.Entity.User;
import com.developerDev.Libris.Repository.UserReopository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdminUserService {


    private final UserReopository reopository;

    public AdminUserService(UserReopository reopository) {
        this.reopository = reopository;
    }


    public List<User> getUsers(){
        return reopository.findAll();
    }



}
