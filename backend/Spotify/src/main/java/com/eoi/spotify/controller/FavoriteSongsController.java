package com.eoi.spotify.controller;

import com.eoi.spotify.entity.FavoriteSongs;
import com.eoi.spotify.projection.UserFavSongProjection;
import com.eoi.spotify.repository.FavoriteSongRepository;
import com.eoi.spotify.service.FavoriteSongService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/favoritesongs")
public class FavoriteSongsController {

    private final FavoriteSongService fss;

    @GetMapping({"/id/{id}"})
    public List<UserFavSongProjection> getAllFavoriteSongsByUser(@PathVariable int id) {
        return fss.getAllFavoriteSongsByUser(id);

    }

    //?songName={name}&userId={id}"} that's how you should call it
    @GetMapping({"/"})
    public List<UserFavSongProjection> getFavoriteSongsByName(@RequestParam("songName") String name, @RequestParam("userId") int id) {
        return fss.getFavoriteSongByName(name, id);
    }
    /*Pending*/
    @PostMapping

    @DeleteMapping
}
