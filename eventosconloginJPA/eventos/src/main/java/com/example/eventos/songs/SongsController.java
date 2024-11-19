package com.example.eventos.songs;

import java.util.List;

import com.example.eventos.auth.AuthController;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import com.example.eventos.songs.dto.SongDTO;
import com.example.eventos.songs.dto.RespuestaSongDTO;
import com.example.eventos.songs.dto.RespuestaSongsDTO;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;


@RequiredArgsConstructor
@RestController
@RequestMapping("/songs")
public class SongsController {
    private final SongsService songsService;
    private final AuthController ac;

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
    public RespuestaSongDTO postSongs(@RequestHeader("Authorization") String authorizationHeader, @RequestBody @Valid SongDTO eInsert) {

        // Extract the Bearer token from the Authorization header
        String token = authorizationHeader.replace("Bearer ", "");

        // Use the token to get the user ID
        Integer idUser = ac.getUserIdFromToken(token);
        eInsert.setUserId(idUser);
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

//    @PostMapping("/{id}/asistir")
//    @ResponseStatus(HttpStatus.NO_CONTENT)
//    public void asisteEvento(@PathVariable int id) {
//        cancionesService.asistirEvento(id);
//    }
//
//    @DeleteMapping("/{id}/asistir")
//    @ResponseStatus(HttpStatus.NO_CONTENT)
//    public void borraAsistencia(@PathVariable int id) {
//        cancionesService.borraAsistencia(id);
//    }
//
//    @GetMapping("/{id}/usuarios")
//    public RespuestaUsuariosDTO usuariosAsisten(@PathVariable int id) {
//        return new RespuestaUsuariosDTO(cancionesService.getAsistentesEvento(id));
//    }
    
}
