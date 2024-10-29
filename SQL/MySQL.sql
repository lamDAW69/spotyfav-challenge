create schema IF NOT EXISTS spotyfav;

USE spotyfav;

CREATE TABLE Usuario (
    id INT AUTO_INCREMENT PRIMARY KEY,
    imagen VARCHAR(255),
    contrasena VARCHAR(255) NOT NULL,
    correo VARCHAR(255) NOT NULL UNIQUE,
    nombre VARCHAR(255) NOT NULL
);

CREATE TABLE UsuarioCancion (
    id INT AUTO_INCREMENT PRIMARY KEY,
    usuarioId INT,
    cancionId INT,
    FOREIGN KEY (usuarioId) REFERENCES Usuario(id)
);