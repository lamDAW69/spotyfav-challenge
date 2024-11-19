package com.example.eventos.songs.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data @NoArgsConstructor
public class SongDTO {
//    @NotBlank(message = "La canción no puede estar vacía")
//    @Size(min = 4, message = "La canción debe tener al menos 4 caracteres")
    private String songName;
//    @NotBlank(message = "La descripción no puede estar vacía")
    String artist;
//    @Positive(message = "El precio no puede ser negativo")
    private String album;
    private Integer userId;

}
