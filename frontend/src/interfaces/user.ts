import { Song } from "./song";

export interface LoginData {
    nombre: string;
    correo: string;
    password: string;
}

export interface UserLogged {
    nombre: string;
    correo: string,
    avatar: string;
    cancionesFavoritas: Song[];
}

export interface RegisterData {
    nombre?: string;
    correo?: string;
    avatar?: string;
    password?: string;
    cancionesFavoritas?: Song[];
    
}