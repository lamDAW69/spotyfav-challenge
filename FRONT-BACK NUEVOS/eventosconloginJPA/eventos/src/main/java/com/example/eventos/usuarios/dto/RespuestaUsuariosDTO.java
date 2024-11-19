package com.example.eventos.usuarios.dto;

import java.util.List;

import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.example.eventos.usuarios.User;

import lombok.Data;

@Data
public class RespuestaUsuariosDTO {
    private List<User> users;

    public RespuestaUsuariosDTO(List<User> users) {
        String baseUrl = ServletUriComponentsBuilder.fromCurrentContextPath().build().toUriString();

        this.users = users.stream().map(u -> {
            return new User(u.getId(), u.getNombre(), u.getCorreo(), null, baseUrl + "/" + u.getAvatar(), u.getFavoriteSongs());
        }).toList();
    }
}
