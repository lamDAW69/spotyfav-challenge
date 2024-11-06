package com.eoi.spotify.repository;


import com.eoi.spotify.entity.FavoriteSongs;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FavoriteSongRepository extends JpaRepository<FavoriteSongs, Integer> {
}
