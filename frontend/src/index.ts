import { AuthService } from "./clases/auth-service";
import { SongsService } from "./clases/songs-service";
import { Song } from "./interfaces/song";
import {
  getSpotifyAccessToken,
  searchSpotifyTracks,
} from "./clases/spotify.ts";
import { UserLoggedService } from "./clases/user-service.ts";


if (!localStorage.getItem("token")) {
  location.assign("login.html");
}

const songsService = new SongsService();
const authService = new AuthService();
const userLoggedService = new UserLoggedService();

const eventoTemplate = document.getElementById(
  "eventoTemplate"
) as HTMLTemplateElement;
const cardContainer = document.getElementById(
  "cardContainer"
) as HTMLDivElement;

async function cargarSongs() {
  const songs = await songsService.getSongs();
  songs.forEach(addEvento);
}

async function addEvento(song: Song) {
  const clone = eventoTemplate.content.cloneNode(true) as DocumentFragment;
  // Obtener el token de acceso de Spotify
  const token = await getSpotifyAccessToken();
  if (!token) {
    console.error("No se pudo obtener el token de acceso de Spotify");
    return;
  }

  const songInfo = await searchSpotifyTracks(song.songName, token);
  if (!songInfo || songInfo.length === 0) {
    console.error("No se encontró información de la canción en Spotify");
    return;
  }

  const albumImageUrl = songInfo[0].url;

  // console.log("informacion de la busqueda", songInfo);

  const img = clone.querySelector(".card-img-top") as HTMLImageElement;
  img.src = albumImageUrl;
  clone.querySelector(".card-title")!.textContent = song.songName;
  clone.querySelector(".card-subtitle")!.textContent = song.artist;
  clone.querySelector(".card-text")!.textContent = song.album;

  const col = clone.firstElementChild!; // Obtenemos la referencia al div.col antes de insertar el template
  clone.querySelector("button.delete")!.addEventListener("click", async () => {
    await songsService.deleteSong(song.id!);
    col.remove(); // Borramos el div.col
  });

  cardContainer.append(clone);
}

/* MAIN */
cargarSongs();

document.getElementById("logout")?.addEventListener("click", () => {
  authService.logout(); // Borra el token
  location.assign("login.html");
});

document.addEventListener("DOMContentLoaded", async () => {
  const profilePic = document.getElementById("profilePic") as HTMLImageElement;
  const correo = document.getElementById("correo") as HTMLSpanElement;

  // Simulación de obtener datos del usuario (puedes reemplazar esto con una llamada a tu API)
  const resp = await userLoggedService.getUserLogged();
  console.log("Usuario log", resp.correo);

  // Actualizar el DOM con los datos del usuario
  profilePic.src = resp.avatar;
  correo.textContent = resp.correo;
});

document.getElementById("correo")?.addEventListener("click", () => {

  location.assign("profile.html");
});


// Manejo del botón de logout
document.getElementById("logout")?.addEventListener("click", () => {
  // Lógica para cerrar sesión
  alert("Sesión cerrada");
  window.location.href = "login.html";
});
