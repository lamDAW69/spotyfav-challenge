package com.example.eventos.songs;

import com.example.eventos.songs.dto.SongDTO;
import com.example.eventos.usuarios.User;

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
