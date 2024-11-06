package com.eoi.spotify.service;


import com.eoi.spotify.entity.FavoriteSongs;
import com.eoi.spotify.repository.FavoriteSongRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Optional;
import java.util.List;

@Service
@RequiredArgsConstructor
public class FavoriteSongService {

    private final FavoriteSongRepository fsr;
    private final UserService us;


    // Save a favorite song
    public FavoriteSongs saveFavoriteSong(Integer userId, Integer songId) {
        us.getUserById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        FavoriteSongs fs = new FavoriteSongs();
        fs.setUserId(userId);
        fs.setSongId(songId);
        return fsr.save(fs);
    }

    ///Select all favorite songs from a user
    public List<FavoriteSongs> getFavoriteSongByUser(int userId) {
        us.getUserById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        List<FavoriteSongs> songsByUser = new ArrayList<>();
        fsr.findAll().forEach(song -> {
            if (song.getUserId() == userId) {
                songsByUser.add(song);
            }
        });

        return songsByUser;
    }

    public List<FavoriteSongs> getFavoriteSongByName(String songName, Integer userId) {

        return fsr.findBySongNameAndUserId(songName, userId);
    }





}
