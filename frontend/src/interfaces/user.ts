export interface LoginData {
    nombre: string;
    correo: string;
    password: string;
}

export interface UserLogged {
    nombre: string;
    correo: string,
    avatar: string;
}

export interface RegisterData {
    nombre?: string;
    correo?: string;
    password?: string;
    avatar?: string;
}