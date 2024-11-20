import { RegisterData, UserLogged } from '../interfaces/user';
import { Http } from './http';
import { SERVER } from '../constants';
import { UserLoggedResponse } from '../interfaces/responses';

export class UserLoggedService {
    #http: Http = new Http();

    async getUserLogged(): Promise<UserLogged> {
        const resp = await this.#http.get<UserLoggedResponse>(`${SERVER}/usuarios/me`);

        return resp.user;
    }

    async updateUserLogged(user: RegisterData): Promise<UserLogged> {
        const resp = await this.#http.put<UserLoggedResponse, RegisterData>(`${SERVER}/usuarios`, user);

        return resp.user;
    }

    // async addSong(song: Song): Promise<Song>  {

    //     try {
    //         const resp = await this.#http.post<SongResponse, Song>(`${SERVER}/songs`, song);
    //         return resp.song;
    //     } catch (error) {
    //         alert('Ya existe la canción.');
    //         throw error;
    //     }
    // }

    // deleteSong(id: number): Promise<void> {
    //     return this.#http.delete<void>(`${SERVER}/songs/${id}`);
    // }
}