package com.example.eventos.songs;

import java.util.List;
import java.util.Objects;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.example.eventos.songs.dto.SongDTO;
import com.example.eventos.usuarios.User;
import com.example.eventos.usuarios.UsuariosRepository;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class SongsService {
    private final @NonNull SongsRepository songsRepository;
    private final @NonNull UsuariosRepository usuariosRepository;
//    private final @NonNull ImageUtils imageUtils;

    public List<Song> getAll() {
        return songsRepository.findBy();
    }

    public Song getById(int id) {
        Song e = songsRepository.findSongById(id);
        return e;
    }

    public Song insert(SongDTO songDTO) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Integer idAuth = Integer.parseInt(auth.getCredentials().toString());
        User user = new User();
        user.setId(idAuth);

        Song song = songsRepository.save(Song.fromDTO(songDTO));
        return songsRepository.findSongById(song.getId());
    }

    public Song update(int id, SongDTO songDTO) {
        Song song = getAndCheckSong(id);
        song = Song.fromDTO(songDTO);
        song.setId(id);
        songsRepository.save(song);
        return songsRepository.findSongById(song.getId());
    }

    public List<Song> findByCreador(int userId) {
        return songsRepository.findByCreador(userId);
    }

    public void delete(int idSong) {
        Song song = getAndCheckSong(idSong);
        songsRepository.delete(song);
    }

//    public void asistirEvento(int idEvento) {
//        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
//        Integer idAuth = Integer.parseInt(auth.getCredentials().toString());
//
//        User user = usuariosRepository.findById(idAuth).get();
//
//        Song song = songsRepository.findById(idEvento)
//                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Evento no encontrado"));
//
//        if(song.getUsers().contains(user)) {
//            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "El usuario ya asiste a este evento");
//        }
//        song.getUsers().add(user);
//        user.getSongs().add(song);
//        songsRepository.flush();
//    }
//
//    public void borraAsistencia(int idEvento) {
//        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
//        Integer idAuth = Integer.parseInt(auth.getCredentials().toString());
//
//        User user = usuariosRepository.findById(idAuth).get();
//
//        Song song = songsRepository.findById(idEvento)
//                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Evento no encontrado"));
//
//        user.getSongs().remove(song);
//        song.getUsers().remove(user);
//        songsRepository.flush();
//    }
//
//    public List<User> getAsistentesEvento(int idEvento) {
//        Song song = songsRepository.findById(idEvento)
//                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Evento no encontrado"));
//
//        return song.getUsers();
//    }

    private Song getAndCheckSong(int id) {
        Song song = songsRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Canción no encontrado"));
        
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Integer idAuth = Integer.parseInt(auth.getCredentials().toString());
        if(!Objects.equals(song.getCreador(), idAuth)) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "El evento no es tuyo");
        }
        return song;
    }
}
