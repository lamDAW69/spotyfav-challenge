package com.eoi.spotyfav.usuarios;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UsuariosRepository extends JpaRepository<User, Integer>{

    List<User> findAll();
    User findUsuarioById(int id);
    Optional<User> findByCorreoAndPassword(String correo, String password);
}
