package com.eoi.spotyfav.songs.dto;

import com.eoi.spotyfav.songs.Song;
import lombok.Data;

import java.util.List;

@Data
public class RespuestaSongsDTO {
    private List<Song> songs;

    public RespuestaSongsDTO(List<Song> songs) {
                this.songs = songs;
    }
}
