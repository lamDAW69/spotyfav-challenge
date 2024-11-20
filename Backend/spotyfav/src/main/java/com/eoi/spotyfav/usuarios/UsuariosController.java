package com.eoi.spotyfav.usuarios;


import com.eoi.spotyfav.songs.Song;
import com.eoi.spotyfav.songs.SongsService;
import com.eoi.spotyfav.songs.dto.RespuestaSongsDTO;
import com.eoi.spotyfav.usuarios.dto.RespuestaUsuarioDTO;
import com.eoi.spotyfav.usuarios.dto.RespuestaUsuariosDTO;
import com.eoi.spotyfav.usuarios.dto.UsuarioDTO;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.security.NoSuchAlgorithmException;
import java.util.List;

@RestController
@RequestMapping("/usuarios")
@RequiredArgsConstructor
public class UsuariosController {
    private final UsuariosService usuariosService;
    private final SongsService songsService;

    @GetMapping
    public RespuestaUsuariosDTO getAll() {
        return new RespuestaUsuariosDTO(usuariosService.getAll());
    }

    @GetMapping("/{id}")
    public RespuestaUsuarioDTO getById(@PathVariable int id) {
        return new RespuestaUsuarioDTO(usuariosService.getById(id));
    }

    @GetMapping("/me")
    public RespuestaUsuarioDTO getMe() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Integer idAuth = Integer.parseInt(auth.getCredentials().toString());
        return new RespuestaUsuarioDTO(usuariosService.getById(idAuth));
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
    public RespuestaSongsDTO getUserSongs() {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Integer idAuth = Integer.parseInt(auth.getCredentials().toString());

        List<Song> songs = songsService.findByCreador(idAuth);
        return new RespuestaSongsDTO(songs);
    }


}
