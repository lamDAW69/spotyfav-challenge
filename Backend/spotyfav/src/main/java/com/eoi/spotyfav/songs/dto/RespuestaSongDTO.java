package com.eoi.spotyfav.songs.dto;


import com.eoi.spotyfav.songs.Song;
import lombok.Data;

@Data
public class RespuestaSongDTO {
    private Song song;

    public RespuestaSongDTO(Song e) {


        this.song = new Song(e.getId(), e.getSongName(), e.getArtist(),
                e.getAlbum(), e.getCreador());

    }
}
