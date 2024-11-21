package com.eoi.spotyfav.usuarios.dto;

import com.eoi.spotyfav.songs.Song;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;

@Data
@NoArgsConstructor
public class UsuarioUpdateDTO {
    private String nombre;
    private String correo;
    private String password;
    private String avatar;

    private List<Song> favoriteSongs;
}

