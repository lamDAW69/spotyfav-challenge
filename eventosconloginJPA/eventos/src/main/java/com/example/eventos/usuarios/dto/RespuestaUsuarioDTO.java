package com.example.eventos.usuarios.dto;

import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.example.eventos.usuarios.User;

import lombok.Data;

@Data
public class RespuestaUsuarioDTO {
    private User user;

    public RespuestaUsuarioDTO(User u) {
        String baseUrl = ServletUriComponentsBuilder.fromCurrentContextPath().build().toUriString();

        this.user = new User(u.getId(), u.getNombre(), u.getCorreo(), null, baseUrl + "/" + u.getAvatar(), u.getFavoriteSongs());
    }
}
