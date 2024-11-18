package com.eoi.spotify.service;


import com.eoi.spotify.entity.FavoriteSongs;
import com.eoi.spotify.projection.UserFavSongProjection;
import com.eoi.spotify.repository.FavoriteSongRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class FavoriteSongService {

    private final FavoriteSongRepository fsr;
    private final UserService us;


    public List<FavoriteSongs> getAllFavoriteSongs() {
        return fsr.findAll();
    }


    public FavoriteSongs saveFavoriteSong(FavoriteSongs favoriteSong) {
        FavoriteSongs fs = fsr.save(favoriteSong);
        return fsr.findById(fs.getId()).get();
    }


    public List<FavoriteSongs> getFavoriteSongByUser(int userId) {
        if (us.getUserById(userId) == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found");
        }
        List<FavoriteSongs> songsByUser = new ArrayList<>();
        fsr.findAll().forEach(song -> {
            if (song.getUserId() == userId) {
                songsByUser.add(song);
            }
        });

        return songsByUser;
    }

    public FavoriteSongs getFavoriteSongById(Integer id) {

        return fsr.findById(id).get();
    }


    public List<UserFavSongProjection> getFavoriteSongByName(String songName, Integer userId) {

        return fsr.findBySongNameAndUserId(songName, userId);
    }


    public List<UserFavSongProjection> getAllFavoriteSongsByUser(int userId) {
        return fsr.findByUserId(userId);
    }


    public void deleteSongById(Integer id) {
        fsr.deleteById(id);
    }

}
