import { Song } from "./song";
import { UserLogged } from "./user";

export interface SongsResponse {
    songs: Song[];
}

export interface SongResponse {
    song: Song;
}

export interface LoginResponse {
    accessToken: string;
}

export interface UserLoggedResponse {
    user: UserLogged;
}
