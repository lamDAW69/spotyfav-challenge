package com.eoi.spotify.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.eoi.spotify.entity.FavoriteSongs;

import java.util.Set;

@Data  //This is for the getters and setters
@AllArgsConstructor //This is for the constructor
@NoArgsConstructor
@Entity
@Table(name = "usuario")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String image;
    private String password;
    private String mail;
    private String name;

    //Relationship to FavSong

    @OneToMany(mappedBy = "User", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<FavoriteSongs> favoriteSongs;
}
