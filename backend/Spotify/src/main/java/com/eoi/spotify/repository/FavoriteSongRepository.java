package com.eoi.spotify.repository;


import com.eoi.spotify.entity.FavoriteSongs;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FavoriteSongRepository extends JpaRepository<FavoriteSongs, Integer> {
    @Query("SELECT fs FROM FavoriteSongs  fs WHERE fs.songName LIKE %:name% AND fs.userId = :userId")
    List<FavoriteSongs> findBySongNameAndUserId(@Param("name") String name, @Param("userId") Integer userId);
}
