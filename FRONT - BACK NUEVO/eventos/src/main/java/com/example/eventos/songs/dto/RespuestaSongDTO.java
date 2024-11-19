package com.example.eventos.songs.dto;

import com.example.eventos.songs.Song;
import com.example.eventos.usuarios.User;

import com.example.eventos.usuarios.UsuariosRepository;
import lombok.Data;

@Data
public class RespuestaSongDTO {
    private Song song;

    public RespuestaSongDTO(Song e) {


        this.song = new Song(e.getId(), e.getSongName(), e.getArtist(),
                e.getAlbum(), e.getCreador());

    }
}
