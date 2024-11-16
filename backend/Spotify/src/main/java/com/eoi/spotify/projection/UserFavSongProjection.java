package com.eoi.spotify.projection;

public interface UserFavSongProjection {
    Integer getSongId();
    String getSongName();
    String getArtist();
    String getAlbum();
    Integer getUserId();
}
