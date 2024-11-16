package com.eoi.spotify.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "favorite_song")
public class FavoriteSongs {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "user_id", nullable = false)
    private Integer userId;

//    @Column(name = "song_id", nullable = false)
//    private Integer songId;
    @Column(name = "song_name")
    private String songName;
    @Column(name = "artist")
    private String artist;
    @Column(name = "album")
    private String album;
}