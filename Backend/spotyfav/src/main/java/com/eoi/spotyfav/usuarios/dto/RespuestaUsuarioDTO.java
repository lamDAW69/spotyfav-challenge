package com.eoi.spotyfav.usuarios.dto;


import com.eoi.spotyfav.usuarios.User;
import lombok.Data;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@Data
public class RespuestaUsuarioDTO {
    private User user;

    public RespuestaUsuarioDTO(User u) {
        String baseUrl = ServletUriComponentsBuilder.fromCurrentContextPath().build().toUriString();

        this.user = new User(u.getId(), u.getNombre(), u.getCorreo(), null, baseUrl + "/" + u.getAvatar(), u.getFavoriteSongs());
    }
}
