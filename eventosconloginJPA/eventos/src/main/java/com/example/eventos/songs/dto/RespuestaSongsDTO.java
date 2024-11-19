package com.example.eventos.songs.dto;

import java.util.List;

import com.example.eventos.songs.Song;

import lombok.Data;

@Data
public class RespuestaSongsDTO {
    private List<Song> songs;

    public RespuestaSongsDTO(List<Song> songs) {
                this.songs = songs;
    }
}
