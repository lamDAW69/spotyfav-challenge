package com.eoi.spotyfav.songs;


import com.eoi.spotyfav.auth.AuthController;
import com.eoi.spotyfav.songs.dto.RespuestaSongDTO;
import com.eoi.spotyfav.songs.dto.RespuestaSongsDTO;
import com.eoi.spotyfav.songs.dto.SongDTO;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RequiredArgsConstructor
@RestController
@RequestMapping("/songs")
public class SongsController {
    private final SongsService songsService;


    @GetMapping
    public RespuestaSongsDTO getAll() {
        List<Song> songs = songsService.getAll();
        return new RespuestaSongsDTO(songs);
    }

    @GetMapping("/{id}")
    public RespuestaSongDTO getSongs(@PathVariable int id) {
        Song e = songsService.getById(id);
        return new RespuestaSongDTO(e);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public RespuestaSongDTO postSongs(@RequestBody @Valid SongDTO eInsert) {

        Song e = songsService.insert(eInsert);
        return new RespuestaSongDTO(e);
    }

    @PutMapping("/{id}")
    public RespuestaSongDTO updateSong(@PathVariable int id, @RequestBody @Valid SongDTO eUpdate) {
        Song e = songsService.update(id, eUpdate);
        return new RespuestaSongDTO(e);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable int id) {
        songsService.delete(id);
    }

    
}
