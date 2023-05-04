package com.springboot.buildingProject.services;

import com.springboot.buildingProject.models.User;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService {
//    boolean createUser(User user);
//    void deleteUser(User user);
    User getCurrentUser();
}
