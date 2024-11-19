package com.eoi.spotyfav.songs;


import com.eoi.spotyfav.songs.dto.SongDTO;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "favorite_song")
public class Song {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "song_name")
    private String songName;
    @Column(name = "artist")
    private String artist;
    @Column(name = "album")
    private String album;

    @Column(name = "user_id", nullable = false)
    Integer creador;


    static Song fromDTO(SongDTO songDTO) {
        return new Song(0, songDTO.getSongName(), songDTO.getArtist(), songDTO.getAlbum(), songDTO.getUserId());
    }
}
