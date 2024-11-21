package com.eoi.spotyfav.songs;

import com.eoi.spotyfav.songs.dto.SongDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SongsRepository extends JpaRepository<Song, Integer> {

    List<Song> findBy();
    Song findSongById(int id);
    List<Song> findByCreador(int userId);
    void deleteByCreador(int userId);


}
