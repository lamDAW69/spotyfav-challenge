package com.eoi.spotyfav.songs;


import com.eoi.spotyfav.songs.dto.SongDTO;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class SongsService {
    private final @NonNull SongsRepository songsRepository;

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
        songDTO.setUserId(idAuth);

        List<Song> cancionesUsuario = songsRepository.findByCreador(idAuth);


        boolean existeCancion = cancionesUsuario.stream().anyMatch(cancion ->
                        cancion.getSongName().equals(songDTO.getSongName()) &&
                        cancion.getArtist().equals(songDTO.getArtist())  &&
                        cancion.getAlbum().equals(songDTO.getAlbum())  &&
                        cancion.getCreador().equals(songDTO.getUserId()));

        System.out.println("------------> " + existeCancion);

        if(existeCancion) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "El evento no es tuyo");
        }
        
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
