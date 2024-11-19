package com.example.eventos.usuarios;

import java.security.NoSuchAlgorithmException;
import java.util.List;

import com.example.eventos.auth.AuthController;
import com.example.eventos.songs.Song;
import com.example.eventos.songs.SongsService;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import com.example.eventos.songs.dto.RespuestaSongsDTO;
import com.example.eventos.usuarios.dto.RespuestaUsuarioDTO;
import com.example.eventos.usuarios.dto.RespuestaUsuariosDTO;
import com.example.eventos.usuarios.dto.UsuarioDTO;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/usuarios")
@RequiredArgsConstructor
public class UsuariosController {
    private final UsuariosService usuariosService;
    private final SongsService songsService;
    private final AuthController ac;

    @GetMapping
    public RespuestaUsuariosDTO getAll() {
        return new RespuestaUsuariosDTO(usuariosService.getAll());
    }

    @GetMapping("/{id}")
    public RespuestaUsuarioDTO getById(@PathVariable int id) {
        return new RespuestaUsuarioDTO(usuariosService.getById(id));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public RespuestaUsuarioDTO insert(@RequestBody @Valid UsuarioDTO u) throws NoSuchAlgorithmException {       
        return new RespuestaUsuarioDTO(usuariosService.insert(u));
    }

    @PutMapping
    @ResponseStatus(HttpStatus.OK)
    public RespuestaUsuarioDTO update(@RequestBody @Valid UsuarioDTO u) {      
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Integer idAuth = Integer.parseInt(auth.getCredentials().toString());
        return new RespuestaUsuarioDTO(usuariosService.update(idAuth, u));
    }
    
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable int id) {
        usuariosService.delete(id);
    }

    @GetMapping("canciones")
    public RespuestaSongsDTO getUserSongs(@RequestHeader("Authorization") String authorizationHeader) {
        // Extract the Bearer token from the Authorization header
        String token = authorizationHeader.replace("Bearer ", "");

        // Use the token to get the user ID
        Integer idUser = ac.getUserIdFromToken(token);
        System.out.println("-------------->" + idUser);
        List<Song> songs = songsService.findByCreador(idUser);
        return new RespuestaSongsDTO(songs);
    }

//    @GetMapping("/{id}/eventos")
//    public RespuestaSongsDTO getEventosUsuario(@PathVariable int id) {
//        return new RespuestaSongsDTO(usuariosService.getEventosAsiste(id));
//    }
//
//    @GetMapping("/{id}/creados")
//    public RespuestaSongsDTO getEventosCreados(@PathVariable int id) {
//        return new RespuestaSongsDTO(usuariosService.getEventosCreados(id));
//    }
}
