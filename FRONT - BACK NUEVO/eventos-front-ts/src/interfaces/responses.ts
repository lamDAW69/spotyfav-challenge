import { Song } from "./song";

export interface SongsResponse {
    songs: Song[];
}

export interface SongResponse {
    song: Song;
}

export interface LoginResponse {
    accessToken: string;
}