package com.eoi.spotify.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "usuariocancion")
public class FavoriteSongs {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "user_id", nullable = false) // Almacenamos solo el ID del usuario
    private Integer userId; // El ID del usuario (no la entidad completa)

    private Integer songId;
    private String songName;

}
