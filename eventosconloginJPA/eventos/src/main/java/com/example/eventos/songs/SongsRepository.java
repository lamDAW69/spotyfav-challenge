package com.example.eventos.songs;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SongsRepository extends JpaRepository<Song, Integer> {
    List<Song> findBy();

    Song findSongById(int id);

    List<Song> findByCreador(int userId);

    // Update nativo
    // @Query(value = "update evento set titulo = :titulo, descripcion = :descripcion, fecha = :fecha where id = :id", nativeQuery = true)
    // void updateEvento(int id, String titulo, String descripcion, LocalDate fecha);
}
