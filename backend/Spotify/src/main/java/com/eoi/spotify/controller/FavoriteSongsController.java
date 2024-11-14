package com.eoi.spotify.controller;

import com.eoi.spotify.entity.FavoriteSongs;
import com.eoi.spotify.entity.User;
import com.eoi.spotify.projection.UserFavSongProjection;
import com.eoi.spotify.repository.FavoriteSongRepository;
import com.eoi.spotify.service.FavoriteSongService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/favoritesongs")
public class FavoriteSongsController {

    private final FavoriteSongService fss;

    //?id={id} Get all favorite songs from a user
    @GetMapping({"/id/{id}"})
    public List<UserFavSongProjection> getAllFavoriteSongsByUser(@PathVariable int id) {
        return fss.getAllFavoriteSongsByUser(id);

    }

    //?songName={name}&userId={id}"} Get a favorite song by name
    @GetMapping({"/"})
    public List<UserFavSongProjection> getFavoriteSongsByName(@RequestParam("songName") String name, @RequestParam("userId") int id) {
        return fss.getFavoriteSongByName(name, id);
    }

    //?id={id}&userId={userId} Delete a favorite song from a user
    @DeleteMapping({"/{id}"})
    public void deleteFavoriteSong(@PathVariable int id, @RequestParam("userId") int userId) {
        fss.deleteFavoriteSong(id, userId);
    }

    //?songId={songId}&userId={userId}
//    @PostMapping({"/"})
//    public FavoriteSongs saveFavoriteSong(@RequestParam("songId") int songId, @RequestParam("userId")  int userId) {
//        return fss.saveFavoriteSong(userId, songId);
//    }

    //Insert a new Song with user ID
    @PostMapping
    public FavoriteSongs insertSong(@RequestBody FavoriteSongs fs) {
        return fss.saveFavoriteSong( fs.getSongId(), fs);
    }

    //Toy metiendo cosas
    //Sigues cocinando no?
    //Si, estoy haciendo un arroz
    //Que rico
    //Si, me encanta cocinar
    //A mi

}
