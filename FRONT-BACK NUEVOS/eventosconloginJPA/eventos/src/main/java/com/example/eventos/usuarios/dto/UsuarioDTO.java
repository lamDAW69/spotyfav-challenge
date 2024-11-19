package com.example.eventos.usuarios.dto;

import com.example.eventos.songs.Song;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data @NoArgsConstructor
public class UsuarioDTO {
    @NotBlank(message = "El nombre no puede estar vacío")
    private String nombre;
    @NotBlank(message = "El correo el obligatorio")
    @Email(message = "El correo no tiene un formato correcto")
    private String correo;
    @NotBlank(message = "El password es obligatorio")
    private String password;
    @NotBlank(message = "El avatar es obligatorio")
    private String avatar;

    private List<Song> favoriteSongs;
}
