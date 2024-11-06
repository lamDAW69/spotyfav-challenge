package com.eoi.spotify.controller;


import com.eoi.spotify.entity.User;
import com.eoi.spotify.projection.UserProjection;
import com.eoi.spotify.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {
    private final UserService us; //Service injection

    //Method to get all users
    @GetMapping
    public List<User> getAllUsers() {
        return us.getAllUsers();  //Call the method from the service
    }

    //This one only get the projected users, based on the query on the repository
    @GetMapping("/projected")
    public List<UserProjection> getAllProjectedUsers() {
        return us.getAllProjectedUsers();
    }
}
