package com.eoi.spotify.controller;


import com.eoi.spotify.entity.User;
import com.eoi.spotify.projection.UserProjection;
import com.eoi.spotify.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {
    private final UserService us;

    @GetMapping
    public List<User> getAllUsers() {
        return us.getAllUsers();
    }

    @GetMapping("/projected")
    public List<UserProjection> getAllProjectedUsers() {
        return us.getAllProjectedUsers();
    }

    @GetMapping("/{id}")
    public User getUserById(@PathVariable int id) {
        return us.getUserById(id);
    }

    @PostMapping
    public User insertUser(@RequestBody User u) {
        return us.insertUser(u);
    }

    @PutMapping("/{id}")
    public User updateUser(@RequestBody User u, @PathVariable int id) {
        return us.updateUser(u , id);
    }

    @DeleteMapping("/{id}")
    public void deleteUserById(@PathVariable int id) {
        us.deleteUserById(id);
    }

}
