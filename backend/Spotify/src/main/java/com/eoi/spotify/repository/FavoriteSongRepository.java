package com.eoi.spotify.repository;


import com.eoi.spotify.entity.FavoriteSongs;
import com.eoi.spotify.projection.UserFavSongProjection;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FavoriteSongRepository extends JpaRepository<FavoriteSongs, Integer> {
    @Query("SELECT fs FROM FavoriteSongs  fs WHERE fs.songName LIKE %:name% AND fs.userId = :userId")
    List<UserFavSongProjection> findBySongNameAndUserId(@Param("name") String name, @Param("userId") Integer userId);

    @Query("SELECT fs FROM FavoriteSongs fs WHERE fs.userId = :userId")
    List<UserFavSongProjection> findByUserId(@Param("userId") Integer userId);
}
