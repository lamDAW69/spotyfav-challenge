package com.eoi.spotify.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@Entity
@Table(name = "user")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "image_user")
    private String image;

    @Column(name ="password_user")  //It needs to have this so that associate with the correct column in the db
    private String password;

    @Column(name ="mail_user")
    private String mail;

    @Column(name ="name_user")
    private String name;

    @OneToMany
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private List<FavoriteSongs> favoriteSongs;
}