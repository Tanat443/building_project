package com.springboot.buildingProject.services;

import com.springboot.buildingProject.models.User;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

public interface UserService extends UserDetailsService {
    //    boolean createUser(User user);
//    void deleteUser(User user);
    User getCurrentUser();
    void createUser(User user);

    List<User> getAllUsers();
}