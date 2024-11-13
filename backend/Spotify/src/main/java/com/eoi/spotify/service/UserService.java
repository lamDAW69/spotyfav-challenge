package com.eoi.spotify.service;


import com.eoi.spotify.entity.User;
import com.eoi.spotify.projection.UserProjection;
import com.eoi.spotify.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
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

    public User getUserById(int id) {
        return ur.findById(id)
                .orElseThrow(() ->
                        new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found", null));
    }
    public User insertUser(User u) {
        u.setId(0);
        return ur.save(u);
    }
    public User updateUser(User u, int id) {
        User existingUser = ur.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User Not found", null));

        if (u.getImage() != null) {
            existingUser.setImage(u.getImage());
        }
        if (u.getPassword() != null) {
            existingUser.setPassword(u.getPassword());
        }
        if (u.getMail() != null) {
            existingUser.setMail(u.getMail());
        }
        if (u.getName() != null) {
            existingUser.setName(u.getName());
        }

        return ur.save(existingUser);
    }
    public void deleteUserById(int id) {
        if(!ur.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND ,"User not found");
        }
        ur.deleteById(id);
    }


}
