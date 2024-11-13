package com.eoi.spotify.controller;

import com.eoi.spotify.entity.FavoriteSongs;
import com.eoi.spotify.projection.UserFavSongProjection;
import com.eoi.spotify.repository.FavoriteSongRepository;
import com.eoi.spotify.service.FavoriteSongService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/favoritesongs")
public class FavoriteSongsController {

    private final FavoriteSongService fss;

    @GetMapping({"/id"})
    public List<UserFavSongProjection> getAllFavoriteSongsByUser(@PathVariable int id) {
        return fss.getAllFavoriteSongsByUser(id);

    }

    @GetMapping({"/name/{name}/user/{id}"})
    public List<UserFavSongProjection> getFavoriteSongsByName(@PathVariable String name, @PathVariable int id) {
        return fss.getFavoriteSongByName(name, id);
    }

}
