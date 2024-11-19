package com.eoi.spotyfav.songs.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data @NoArgsConstructor
public class SongDTO {

    private String songName;
    String artist;
    private String album;
    private Integer userId;

}
