package com.example.eventos.usuarios;

import java.util.List;

import com.example.eventos.songs.Song;
import com.example.eventos.usuarios.dto.UsuarioDTO;

import jakarta.persistence.*;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "user")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name ="name_user")
    private String nombre;
    @Column(name ="mail_user")
    private String correo;
    @Column(name ="password_user")
    private String password;
    @Column(name = "image_user")
    private String avatar;

    @OneToMany
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private List<Song> favoriteSongs;

//    @OneToMany
//    @JoinColumn(name = "user_id", referencedColumnName = "id")
//    private List<Song> favoriteSongs;

    static User fromDTO(UsuarioDTO usuarioDTO) {
        return new User(0, usuarioDTO.getNombre(), usuarioDTO.getCorreo(), usuarioDTO.getPassword(), usuarioDTO.getAvatar(), null);
    }
}
