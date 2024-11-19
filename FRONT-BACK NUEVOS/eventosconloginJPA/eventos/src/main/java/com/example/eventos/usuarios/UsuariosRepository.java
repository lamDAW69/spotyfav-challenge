package com.example.eventos.usuarios;


import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface UsuariosRepository extends JpaRepository<User, Integer>{
    List<User> findBy();

    List<User> findAll();

    User findUsuarioById(int id);

//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private int id;
//    @Column(name ="name_user")
//    private String nombre;
//    @Column(name ="mail_user")
//    private String correo;
//    @Column(name ="password_user")
//    private String password;
//    @Column(name = "image_user")
//    private String avatar;
//
//    @OneToMany
//    @JoinColumn(name = "user_id", referencedColumnName = "id")
//    private List<Song> favoriteSongs;

    @Query("SELECT u.id as id FROM User u WHERE u.correo = :correo")
    Optional<Integer> findIdUserByCorreo(String correo);

    Optional<User> findByCorreoAndPassword(String correo, String password);
}
