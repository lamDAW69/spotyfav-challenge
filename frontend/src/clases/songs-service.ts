import { SERVER } from "../constants";
import { Song } from "../interfaces/song";
import { SongResponse, SongsResponse } from "../interfaces/responses";
import { Http } from "./http";

export class SongsService {
    #http: Http = new Http();

    async getSongs(): Promise<Song[]> {
        const resp = await this.#http.get<SongsResponse>(`${SERVER}/usuarios/canciones`);
         
        return resp.songs;
    }

    async addSong(song: Song): Promise<Song>  {

        try {
            const resp = await this.#http.post<SongResponse, Song>(`${SERVER}/songs`, song);
            return resp.song;
        } catch (error) {
            alert('Ya existe la canción.');
            throw error;
        }
    }

    deleteSong(id: number): Promise<void> {
        return this.#http.delete<void>(`${SERVER}/songs/${id}`);
    }
}