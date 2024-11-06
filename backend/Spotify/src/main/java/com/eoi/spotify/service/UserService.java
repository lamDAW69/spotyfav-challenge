package com.eoi.spotify.service;


import com.eoi.spotify.entity.User;
import com.eoi.spotify.projection.UserProjection;
import com.eoi.spotify.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository ur;

    public List<User> getAllUsers() {
        return ur.findAll();
    }

    public List<UserProjection> getAllProjectedUsers() {
        return ur.findAllUsersProjected();
    }

    public Optional<User> getUserById(int id) {
        return ur.findById(id);
    }
    public User insertUser(User u) {
        u.setId(0);
        return ur.save(u);
    }
    public User updateUser(User u) {
        return ur.save(u);
    }
    public void deleteUser(int id) {
        ur.deleteById(id);
    }


}
