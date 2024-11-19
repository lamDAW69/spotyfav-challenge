package com.eoi.spotyfav.usuarios.dto;


import com.eoi.spotyfav.usuarios.User;
import lombok.Data;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.List;

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
